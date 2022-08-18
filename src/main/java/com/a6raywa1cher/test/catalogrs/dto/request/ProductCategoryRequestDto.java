package com.a6raywa1cher.test.catalogrs.dto.request;

import com.a6raywa1cher.test.catalogrs.validation.group.OnAny;
import com.a6raywa1cher.test.catalogrs.validation.group.OnCreate;
import com.a6raywa1cher.test.catalogrs.validation.group.OnUpdate;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ProductCategoryRequestDto {
    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    @Size(max = 120, groups = OnAny.class)
    private String title;

    @Size(max = 120, groups = OnAny.class)
    private String description;
}
