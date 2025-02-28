package com.aplavina.bookspageable.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {
    Long id;
    @NotNull
    @NotBlank
    String name;
    @NotNull
    Long authorId;
}