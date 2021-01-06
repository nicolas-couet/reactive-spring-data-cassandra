package com.acme.core.utils;

import java.util.Locale;

public class StringUtils {
    public static String toLowerCase(String string){
        Assert.notNull(string, "String must not be null");
        return string.toLowerCase(Locale.ROOT);
    }
}
