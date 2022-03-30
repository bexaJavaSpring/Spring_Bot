package com.example.spring_bot.entity;

import com.example.spring_bot.entity.templete.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbsEntity {
    private String chatId;

    private String fullName;
    private float lat;
    private float lon;

    private String phone;
    private String lang;
    private String state;


}
