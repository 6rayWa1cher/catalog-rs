package com.a6raywa1cher.test.catalogrs.rest.dto;

import com.a6raywa1cher.test.catalogrs.rest.dto.group.OnCreate;
import com.a6raywa1cher.test.catalogrs.rest.dto.group.OnUpdate;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ProductCategoryRestDto {
    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String description;
}
