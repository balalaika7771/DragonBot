package i_zhendorenko.dragCaveBot.util;

import i_zhendorenko.dragCaveBot.POJO.Code;
import i_zhendorenko.dragCaveBot.POJO.DragonPOJO;
import i_zhendorenko.dragCaveBot.models.Dragon;
import i_zhendorenko.dragCaveBot.services.DragonService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
@Service
public class ResponseEjector {

    @Value("#{'${dragonCave.caves}'.split(',')}")
    List<String> caves;

    @Value("${dragonCave.allCaves}")
    String all;

    private final DragonService dragonService;
    private static final Logger logger = LoggerFactory.getLogger(ResponseEjector.class);
    private final StringSimpler stringSimpler;
    public ResponseEjector(DragonService dragonService, StringSimpler stringSimpler) {
        this.dragonService = dragonService;
        this.stringSimpler = stringSimpler;
    }
    public List<Code> ejectCode(String Response){
        List<Code> res = new LinkedList<Code>();

        Pattern pattern = Pattern.compile("href=\"/get/(.{5})\"");
        Matcher matcher = pattern.matcher(Response);

        while (matcher.find()) {
            String href = matcher.group(1);
            res.add(new Code("https://dragcave.net/get/"+href,href));
        }

         pattern = Pattern.compile("href=\"abandoned/([^\"]+)\">");
         matcher = pattern.matcher(Response);

        while (matcher.find()) {
            String fullMatch = matcher.group(1);
            String partBeforeSlash = fullMatch.split("/")[0];
            res.add(new Code("https://dragcave.net/abandoned/"+ fullMatch,partBeforeSlash));
        }

        return  res;
    }
    public List<DragonPOJO> ejectDragon(String html) throws RuntimeException {
        List<DragonPOJO> res = new LinkedList<DragonPOJO>();

        Pattern pattern = Pattern.compile("href=\"/get/(.{5})\"");
        Matcher matcher = pattern.matcher(html);

        List<String> urls = new LinkedList<String>();
        while (matcher.find()) {
            String href = matcher.group(1);
            urls.add("https://dragcave.net/get/"+href);
        }


        Document document = Jsoup.parse(html);
        Elements spans = document.select("div.eggs span");


        Optional<String> cave =
                caves.stream().
                        filter(curcave -> html.contains("Searching for Eggs - " + curcave))
                        .findAny();
        if(cave.isEmpty()){
            return res;
        }
        int i = 0;
        for (Element span : spans) {
            String description = stringSimpler.simple(span.text());
            Optional<List<Dragon>> dragons = dragonService.findByDescription(description);

            int finalI = i++;
            List<DragonPOJO> newDragon = dragons.get()
                    .stream()
                    .filter(dragon -> dragon.getHabitat()
                            .stream()
                            .anyMatch(cave.get()::contains))
                    .map(dragon -> new DragonPOJO(urls.get(finalI),dragon.getName()))
                    .collect(Collectors.toList());
            if(newDragon.isEmpty()){
                System.out.println("ALERT ZERO: " + dragons.get() + description+ " anyway: "+ newDragon);

                newDragon = dragons.get().stream().map(dragon -> new DragonPOJO(urls.get(finalI),dragon.getName())).collect(Collectors.toList());
                // logger.warn("ALERT ZERO: " + dragons.get() + description+ " anyway: "+ newDragon);
            }
            if(newDragon.size()>1){
                //  logger.warn("ALERT MORE THAN ONE:"+dragons.get() + description);
                System.out.println("ALERT MORE THAN ONE:"+dragons.get() + description);
            }
            res.addAll(newDragon);
        }
        System.out.println(res);
        return  res;
    }

    public List<Dragon> ejectNewDragon(String html){
        List<Dragon> res = new LinkedList<Dragon>();

        Document document = Jsoup.parse(html);
        Elements rows = document.select("table.article-table tr");

        for (Element row : rows) {
            Elements columns = row.select("td");

            if (columns.size() >= 4) { // Проверка на наличие нужного количества столбцов
                Dragon dragon = new Dragon();

                dragon.setName(columns.get(2).select("a").text());

                String description = columns.get(1).text();
                description = description.replaceAll("\\[\\d+\\]", "");

                dragon.setDescription(stringSimpler.simple(description));

                Elements habitatLinks = columns.get(3).select("a");
                List<String> habitatList = new ArrayList<String>();
                for (Element habitatLink : habitatLinks) {

                    if(caves.contains(habitatLink.text())){
                        habitatList.add(habitatLink.text());
                    }
                    if(habitatLink.text().contains(all)){
                        habitatList.addAll(caves);
                    }
                }
                dragon.setHabitat(habitatList);

                res.add(dragon);
            }
        }

        return res;
    }
}
