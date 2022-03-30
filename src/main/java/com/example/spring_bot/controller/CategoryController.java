package com.example.spring_bot.controller;

import com.example.spring_bot.dto.ApiResponse;
import com.example.spring_bot.dto.CategoryDTO;
import com.example.spring_bot.entity.Category;
import com.example.spring_bot.repository.CategoryRepository;
import com.example.spring_bot.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/api/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {
    final CategoryService categoryService;
    final CategoryRepository categoryRepository;

    @GetMapping("/list")
    public HttpEntity<?> getAll() {
        return ResponseEntity.ok().body(categoryRepository.findAll());
    }

    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody CategoryDTO dto) {
        ApiResponse apiResponse = categoryService.add(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @Valid @RequestBody CategoryDTO dto) {
        ApiResponse apiResponse = categoryService.edit(id, dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = categoryService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 204 : 404).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        Optional<Category> byId = categoryRepository.findById(id);
        return ResponseEntity.ok().body(byId.get());
    }

}
