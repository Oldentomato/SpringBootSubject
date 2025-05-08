package com.example.MySpringbootLab.controller;

import com.example.MySpringbootLab.entity.Book;
import com.example.MySpringbootLab.exception.BusinessException;
import com.example.MySpringbootLab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository bookRepository;


    @PostMapping
    public Book create(@RequestBody Book book){
        return bookRepository.save(book);
    }

    @GetMapping
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Optional<Book> optionalBook = bookRepository.findById(id);

        return optionalBook.map(ResponseEntity::ok)
                .orElse(new ResponseEntity("not found", HttpStatus.NOT_FOUND));
    }

    @GetMapping("/isbn/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn){
        Book existBook = getExistBook(bookRepository.findByIsbn(isbn));

        return existBook;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetail){
        Book existBook = getExistBook(bookRepository.findById(id));

        existBook.setTitle(bookDetail.getTitle());
        Book updateBook = bookRepository.save(existBook);
        return ResponseEntity.ok(updateBook);

    }

    private Book getExistBook(Optional<Book> bookRepository) {
        Book existBook = bookRepository.orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
        return existBook;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        Book existBook = getExistBook(bookRepository.findById(id));
        bookRepository.delete(existBook);

        return ResponseEntity.ok(existBook.getTitle() + "가 정상적으로 삭제되었습니다");
    }


}
