package com.a6raywa1cher.test.catalogrs.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductQueryDto {
    private String title;

    private Long category;

    private BigDecimal fromPriceAmount;

    private BigDecimal toPriceAmount;
}
