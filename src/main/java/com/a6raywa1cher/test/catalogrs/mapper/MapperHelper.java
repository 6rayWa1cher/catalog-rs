package com.a6raywa1cher.test.catalogrs.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@Named("MapperHelper")
public class MapperHelper {
    @Named("FromLocalDateTime")
    public ZonedDateTime fromLocalDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return ZonedDateTime.of(dateTime, ZoneId.systemDefault());
    }

    @Named("ToLocalDateTime")
    public LocalDateTime toLocalDateTime(ZonedDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }
}