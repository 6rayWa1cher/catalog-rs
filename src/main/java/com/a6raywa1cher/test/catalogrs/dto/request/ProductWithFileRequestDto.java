package com.a6raywa1cher.test.catalogrs.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Data
public class ProductWithFileRequestDto extends ProductRequestDto {
    private MultipartFile image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductWithFileRequestDto that = (ProductWithFileRequestDto) o;
        return Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), image);
    }
}
