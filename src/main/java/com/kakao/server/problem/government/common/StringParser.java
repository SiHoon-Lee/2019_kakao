package com.kakao.server.problem.government.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringParser {

    private static final String LIMIT_REGEX = "(\\d{1,})";
    private static final String RATE_REGEX = "(\\d+[\\.?\\d+]{0,})";

    private static final Long MILLION = 1000000L;
    private static final Long HUNDRED_MILLION = 100000000L;

    public static Long limitPaser(String limit){
        Matcher matcher = Pattern.compile(StringParser.LIMIT_REGEX).matcher(limit);

        if(matcher.find()){
            return Long.valueOf(matcher.group(1)) * StringParser.findAmountWord(limit);
        }

        return null;
    }

    private static Long findAmountWord(String word){

        if(word.contains("억원")){
            return StringParser.HUNDRED_MILLION;
        } else if(word.contains("백만원")){
            return StringParser.MILLION;
        }

        return 1L;
    }

    public static List<Double> ratePaser(String rate){
        Matcher matcher = Pattern.compile(RATE_REGEX).matcher(rate);

        List<Double> rates = new ArrayList<>();
        while (matcher.find()){
            rates.add(Double.valueOf(matcher.group(0)));
        }

        return rates;
    }

}
