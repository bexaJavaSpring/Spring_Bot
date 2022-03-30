package com.example.spring_bot.entity;

import com.example.spring_bot.entity.templete.AbsNameEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book extends AbsNameEntity {

    @OneToOne
    private Attachment attachment;

    @ManyToOne
    private Category category;

    @Column(columnDefinition = "text")
    private String description;

    private double price;
}
