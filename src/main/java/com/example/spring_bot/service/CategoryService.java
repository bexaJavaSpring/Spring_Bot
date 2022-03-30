package com.example.spring_bot.service;

import com.example.spring_bot.dto.ApiResponse;
import com.example.spring_bot.dto.CategoryDTO;
import com.example.spring_bot.entity.Category;
import com.example.spring_bot.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    final CategoryRepository categoryRepository;


    public ApiResponse add(CategoryDTO dto) {

        if(categoryRepository.existsByNameIgnoreCase(dto.getName())){
              return new ApiResponse("Xatolik",false);
        }
        Category category=new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setActive(dto.isActive());
        categoryRepository.save(category);
        return new ApiResponse("Added",true);
    }

    public ApiResponse edit(Integer id, CategoryDTO dto) {
        Optional<Category> byId = categoryRepository.findById(id);
        if(!byId.isPresent()){
            return new ApiResponse("Not found",false);
        }
        Category category = byId.get();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setActive(dto.isActive());
        categoryRepository.save(category);
        return new ApiResponse("Edited",true);
    }

    public ApiResponse delete(Integer id) {
        Optional<Category> byId = categoryRepository.findById(id);
        Category category = byId.get();
        categoryRepository.delete(category);
        return new ApiResponse("Delete",true);
    }
}
