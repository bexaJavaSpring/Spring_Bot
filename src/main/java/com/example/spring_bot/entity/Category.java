package com.example.spring_bot.entity;

import com.example.spring_bot.entity.templete.AbsNameEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Category extends AbsNameEntity {
    private String description;

    public Category(Integer id, String name, boolean active, String description) {
        super(id, name, active);
        this.description = description;
    }

    public Category() {
    }

}
