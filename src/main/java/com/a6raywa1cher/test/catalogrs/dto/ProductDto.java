package com.a6raywa1cher.test.catalogrs.dto;

import com.a6raywa1cher.test.catalogrs.dao.ProductStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Currency;

@Data
public class ProductDto {
    private Long id;

    private String title;

    private String description;

    private Currency priceCurrency;

    private BigDecimal priceAmount;

    private String imageUrl;

    private Long category;

    private ZonedDateTime createdAt;

    private ProductStatus status;
}
