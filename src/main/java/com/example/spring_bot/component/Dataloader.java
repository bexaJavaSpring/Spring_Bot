//package com.example.spring_bot.component;
//
//import com.example.spring_bot.entity.Book;
//import com.example.spring_bot.entity.Category;
//import com.example.spring_bot.repository.BookRepository;
//import com.example.spring_bot.repository.CategoryRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//@Component
//@RequiredArgsConstructor
//public class Dataloader implements CommandLineRunner {
//
//    @Value("${spring.sql.init.mode}")
//    private String mode;
//
//    final CategoryRepository categoryRepository;
//    final BookRepository bookRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        if (mode.equals("always")) {
//          Category category=categoryRepository.save(new Category(1,"Diniy",true,"Yaxshi"));
//          Category category1=categoryRepository.save(new Category(2,"Jahon",true,"Yaxshi"));
//          Category category2=categoryRepository.save(new Category(3,"Badiiy",true,"Yaxshi"));
//       bookRepository.save(new Book(category,"yaxshi kitob",30000.0));
//        }
//    }
//}
