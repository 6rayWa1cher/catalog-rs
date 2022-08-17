package com.a6raywa1cher.test.catalogrs.mapper;

import com.a6raywa1cher.test.catalogrs.integration.FileStorage;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@Named("MapperHelper")
public class MapperHelper {
    private final FileStorage fileStorage;

    @Autowired
    public MapperHelper(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

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

    @Named("ToPublicUrl")
    public String toPublicUrl(String id) {
        if (id == null) return null;
        return fileStorage.getPublicUrl(id);
    }
}