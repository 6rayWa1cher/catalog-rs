package com.a6raywa1cher.test.catalogrs.component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionView {
    String value();
}
