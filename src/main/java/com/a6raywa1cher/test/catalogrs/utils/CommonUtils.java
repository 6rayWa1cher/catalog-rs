package com.a6raywa1cher.test.catalogrs.utils;

public final class CommonUtils {
    public static <T> T coalesce(T a, T b) {
        return a != null ? a : b;
    }

    public static <T> T coalesce(T a, T b, T c) {
        return a != null ? a : coalesce(b, c);
    }
}