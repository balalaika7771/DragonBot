package i_zhendorenko.dragCaveBot.services;

import i_zhendorenko.dragCaveBot.POJO.Code;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResponseEjector {
    public ResponseEjector() {
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
            res.add(new Code("https://dragcave.net/get/abandoned/"+ fullMatch,partBeforeSlash));
        }

        return  res;
    }

}
