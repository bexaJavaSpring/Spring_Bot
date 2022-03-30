package com.example.spring_bot.controller;

import com.example.spring_bot.dto.ApiResponse;
import com.example.spring_bot.dto.BookDTO;
import com.example.spring_bot.entity.Book;
import com.example.spring_bot.repository.BookRepository;
import com.example.spring_bot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/api/book")
@RestController
public class BookController {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookService bookService;


    @PostMapping
    public HttpEntity<?> save(@Valid @RequestBody BookDTO dto) {
        ApiResponse response = bookService.save(dto);

        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @Valid @RequestBody BookDTO dto) {
        ApiResponse apiResponse = bookService.edit(id, dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = bookService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 404).body(apiResponse);
    }

    @GetMapping("/list")
    public HttpEntity<?> getAll() {
        return ResponseEntity.ok().body(bookRepository.findAll());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        Optional<Book> byId = bookRepository.findById(id);
        Book book = byId.get();
        return ResponseEntity.ok().body(book);
    }
}
