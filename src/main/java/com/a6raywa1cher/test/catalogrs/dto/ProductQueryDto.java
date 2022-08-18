package com.a6raywa1cher.test.catalogrs.dto;

import com.a6raywa1cher.test.catalogrs.dao.ProductStatus;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductQueryDto {
    @Parameter(description = "Part of the title that must be present in the exact order")
    private String title;

    private Long category;

    private BigDecimal fromPriceAmount;

    private BigDecimal toPriceAmount;

    private ProductStatus status;
}
