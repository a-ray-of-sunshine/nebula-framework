package com.nebula.server;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.nebula.utils.XMLPropertyTokensParser;

public class TokenTest {
    
    private static final String TOKEN_TAG = "$";
    private static final String START_TAG = "{";
    private static final String END_TAG = "}";
    
    @Test
    public void test1() throws IOException{
        
        XMLPropertyTokensParser parser = new XMLPropertyTokensParser("jdbc.properties");
        parser.parse("hibernate.cfg.xml");
    }

    // @Test
    public void test(){
        // String reg = ".*?(\\$\\s*\\{([\\w\\.\\s*]*)\\}).*?";
        String reg = "\\" + TOKEN_TAG +  "\\s*\\" + START_TAG + "([\\w\\.\\s*]*)\\" + END_TAG;
        Pattern pattern = Pattern.compile(reg);
        String test = "<property name=\"$ { jdbc.driverClassName }\" value=\"${jdbc.driverClass}\"/>";

        Matcher matcher = pattern.matcher(test);
        while(matcher.find()){
            String token = matcher.group();
            String normal = resolve(token);
            test = test.replace(token, normal);
        }
        
        
        System.out.println(test);
        
        }

    private String resolve(String token) {
        token = token.substring(token.indexOf(START_TAG) + 1, token.indexOf(END_TAG));
        return token.trim();
    }
}