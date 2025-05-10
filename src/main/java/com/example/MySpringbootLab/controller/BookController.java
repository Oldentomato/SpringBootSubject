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
    public ResponseEntity<BookDTO.BookResponse> create(@Valid @RequestBody BookDTO.BookCreateRequest request){
        BookDTO.BookResponse createdBook = bookService.createBook(request);
        return ResponseEntity.ok(createdBook);

    }

    @GetMapping
    //이 부분 정리할것
    public List<BookDTO.BookResponse> getBooks(){
        return bookService.getAllBooks()
                .stream()
                .map(book -> new BookDTO.BookResponse(book))
                .toList();
    }

    @GetMapping("/{id}")
    public BookDTO.BookResponse getBookById(@PathVariable Long id){
        Book existBook = bookService.getBookById(id);

        return new BookDTO.BookResponse(existBook);
    }

    @GetMapping("/isbn/{isbn}")
    public BookDTO.BookResponse getBookByIsbn(@PathVariable String isbn){
        Book existBook = bookService.getBookByIsbn(isbn);

        return new BookDTO.BookResponse(existBook);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookDTO.BookResponse> updateBook(@PathVariable Long id,
                                           @Valid @RequestBody BookDTO.BookUpdateRequest request){
        BookDTO.BookResponse updateBook = bookService.updateBook(request,id);
        return ResponseEntity.ok(updateBook);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }


}
