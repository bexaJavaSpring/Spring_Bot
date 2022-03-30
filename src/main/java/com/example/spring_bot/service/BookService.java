package com.example.spring_bot.service;

import com.example.spring_bot.dto.ApiResponse;
import com.example.spring_bot.dto.BookDTO;
import com.example.spring_bot.entity.Attachment;
import com.example.spring_bot.entity.Book;
import com.example.spring_bot.entity.Category;
import com.example.spring_bot.repository.AttachmentContentRepository;
import com.example.spring_bot.repository.AttachmentRepository;
import com.example.spring_bot.repository.BookRepository;
import com.example.spring_bot.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public ApiResponse save(BookDTO dto) {

        Book book=new Book();
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(dto.getAttachmentId());
        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
         if(!optionalCategory.isPresent()){
            return new ApiResponse("Xatolik",false);
         }
        book.setName(dto.getName());
        book.setPrice(dto.getPrice());
        book.setDescription(dto.getDescription());
        book.setAttachment(optionalAttachment.get());
        book.setCategory(optionalCategory.get());
        bookRepository.save(book);
        return new ApiResponse("Saved!",true);
    }

    public ApiResponse edit(Integer id, BookDTO dto) {
        Optional<Book> byId = bookRepository.findById(id);
        if (!byId.isPresent()) {
            return new ApiResponse("Not found!",false);
        }
        Optional<Category> byId1 = categoryRepository.findById(dto.getCategoryId());
        Optional<Attachment> byId2 = attachmentRepository.findById(dto.getAttachmentId());
        Book book = byId.get();
        book.setName(dto.getName());
        book.setDescription(dto.getDescription());
        book.setPrice(dto.getPrice());
        book.setCategory(byId1.get());
        book.setAttachment(byId2.get());
        Book save = bookRepository.save(book);
        return new ApiResponse("Edited",true,save);
    }

    public ApiResponse delete(Integer id) {
        Optional<Book> byId = bookRepository.findById(id);
        if (!byId.isPresent()) {
            return new ApiResponse("Not found",false);
        }
        Book book = byId.get();
        book.setActive(false);
        bookRepository.save(book);
        return new ApiResponse("Blocked",true);
    }
}
