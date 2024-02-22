package com.findar.book.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateBookDto {
    @DecimalMin(value = "100.0")
    @NotNull(message = " is required")
    private Double price;
    @NotBlank(message = " is required")
    private String title;
}
