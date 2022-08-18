package com.a6raywa1cher.test.catalogrs.utils;

public final class JspUtils {
    public static String upsertQueryParam(String path, String name, String value) {
        String newPart = name + "=" + value;
        int index = path.indexOf(name);
        if (index != -1) {
            return path.replaceFirst("%s=[^&]*".formatted(name), newPart);
        } else {
            return path.contains("?") ? path + "&" + newPart : path + "?" + newPart;
        }
    }
}
