package com.example.spring_bot.repository;

import com.example.spring_bot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    boolean existsByNameIgnoreCase(String name);
}
