package com.a6raywa1cher.test.catalogrs.rest.dto;

import com.a6raywa1cher.test.catalogrs.dao.ProductStatus;
import com.a6raywa1cher.test.catalogrs.rest.dto.group.OnCreate;
import com.a6raywa1cher.test.catalogrs.rest.dto.group.OnUpdate;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class ProductRestDto {
    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    @Size(max = 255)
    private String title;

    private String description;

    @NotNull(groups = {OnCreate.class, OnUpdate.class})
    @PositiveOrZero
    private BigDecimal priceAmount;

    @NotNull(groups = {OnCreate.class, OnUpdate.class})
    @Positive
    private Long category;

    @NotNull
    private ProductStatus status;
}
