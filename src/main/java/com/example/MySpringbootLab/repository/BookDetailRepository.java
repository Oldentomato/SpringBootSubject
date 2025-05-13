package com.example.MySpringbootLab.repository;


import com.example.MySpringbootLab.entity.Book;
import com.example.MySpringbootLab.entity.BookDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByTitleContainingIgnoreCase(String title);
    Optional<Book> findByIdWithBookDetail(Long id);
    Optional<Book> findByIsbnWithBookDetail(String isbn);
    boolean existsByIsbn(String isbn);
}
