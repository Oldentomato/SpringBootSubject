package com.example.MySpringbootLab.repository;

import com.example.MySpringbootLab.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @Test
    void testDeleteBook() {
        Book book = bookRepository.findByIsbn("9788956746425")
                .orElseThrow(()->new RuntimeException("Book Not Found"));

        bookRepository.delete(book);
    }

    @Test
//    @Rollback(value = false)
    void testFindByIsbn(){
        Optional<Book> foundBook = bookRepository.findByIsbn("9788956746425");
        Book foundResult = foundBook.orElseGet(() -> new Book());
        assertThat(foundResult.getTitle()).isEqualTo("스프링 부트 입문");
    }

    @Test
    void testFindByAuthor(){
        List<Book> foundBooks = bookRepository.findByAuthor("박둘리");
        assertThat(foundBooks).isNotEmpty();
    }



    @Test
    void testUpdateBook(){
        Optional<Book> foundBook = bookRepository.findByIsbn("9788956746425");
        Book foundResult = foundBook.orElseGet(() -> new Book());

        foundResult.setPrice(50000);
        assertThat(foundResult.getPrice()).isEqualTo(50000);
    }

    @Test
//    @Rollback(value = false)
    void testCreateBook(){
        Book book = new Book();
        book.setTitle("스프링 부트 입문");
        book.setIsbn("9788956746425");
        book.setAuthor("홍길동");
        book.setPrice(30000);
        book.setPublishDate(LocalDate.parse("2025-05-07"));

        Book book2 = new Book();
        book2.setTitle("JPA 프로그래밍");
        book2.setIsbn("9788956746432");
        book2.setAuthor("박둘리");
        book2.setPrice(35000);
        book2.setPublishDate(LocalDate.parse("2025-04-30"));

        Book addBook = bookRepository.save(book);
        Book addBook2 = bookRepository.save(book2);
        assertThat(addBook).isNotNull();
        assertThat(addBook2).isNotNull();
    }



}
