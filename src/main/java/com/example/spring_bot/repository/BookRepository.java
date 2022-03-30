package com.example.spring_bot.repository;

import com.example.spring_bot.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findAllByCategory_Name(String name);

    Optional<Book> findByName(String name);
}
