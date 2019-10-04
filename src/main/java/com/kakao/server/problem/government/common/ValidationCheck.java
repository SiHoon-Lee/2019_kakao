package com.kakao.server.problem.government.common;

import com.google.common.base.Strings;

public class ValidationCheck {

    public static boolean isEmpty(String str){
        return Strings.isNullOrEmpty(str);
    }

    public static boolean isNotEmpty(String str){
        return !Strings.isNullOrEmpty(str);
    }

    public static boolean isNotNull(Object o){
        return !(o == null);
    }
}
