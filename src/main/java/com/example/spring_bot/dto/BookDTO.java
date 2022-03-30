package com.example.spring_bot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {
    private Integer attachmentId;

    private String name;

    private Integer categoryId;

    private String description;

    private double price;
}
