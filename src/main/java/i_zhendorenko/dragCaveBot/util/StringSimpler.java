package i_zhendorenko.dragCaveBot.util;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StringSimpler {
    public String simple(String str){
        Pattern pattern = Pattern.compile("[^a-zA-Z]");
        Matcher matcher = pattern.matcher(str.toLowerCase());
        return matcher.replaceAll("");
    }
}
