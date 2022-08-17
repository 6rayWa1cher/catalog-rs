package com.a6raywa1cher.test.catalogrs.utils;

import java.util.Map;
import java.util.stream.Collectors;

public final class CollectionUtils {
    public static <T, J> String mapToString(Map<T, J> map, String separator) {
        return map.entrySet().stream()
                .map(e -> "%s = %s".formatted(e.getKey(), e.getValue()))
                .collect(Collectors.joining(separator));
    }
}
