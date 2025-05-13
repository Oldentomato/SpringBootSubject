package com.example.MySpringbootLab.controller;

import com.example.MySpringbootLab.entity.Book;
import com.example.MySpringbootLab.entity.dto.BookDTO;
import com.example.MySpringbootLab.exception.BusinessException;
import com.example.MySpringbootLab.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;


    @PostMapping
    public ResponseEntity<BookDTO.Response> create(@Valid @RequestBody BookDTO.Request request){
        BookDTO.Response createdBook = bookService.createBook(request);
        return ResponseEntity.ok(createdBook);

    }

    @GetMapping
    //이 부분 정리할것
    public ResponseEntity<List<BookDTO.Response>> getBooks(){
        List<BookDTO.Response> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO.Response> getBookById(@PathVariable Long id){
        BookDTO.Response existBook = bookService.getBookById(id);

        return ResponseEntity.ok(existBook);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO.Response> getBookByIsbn(@Valid @PathVariable String isbn){
        BookDTO.Response existBook = bookService.getBookByIsbn(isbn);

        return ResponseEntity.ok(existBook);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookDTO.Response> updateBook(@PathVariable Long id,
                                           @Valid @RequestBody BookDTO.Request request){
        BookDTO.Response updateBook = bookService.updateBook(request,id);
        return ResponseEntity.ok(updateBook);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }


}
