package com.example.spring_bot.repository;

import com.example.spring_bot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByChatId(String chatId);

    Optional<User> findByPhone(String phone);
}
