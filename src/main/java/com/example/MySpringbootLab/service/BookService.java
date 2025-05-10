package com.example.MySpringbootLab.service;

import com.example.MySpringbootLab.entity.Book;
import com.example.MySpringbootLab.entity.dto.BookDTO;
import com.example.MySpringbootLab.exception.BusinessException;
import com.example.MySpringbootLab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBookById(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
    }

    public Book getBookByIsbn(String isbn){
        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
    }

    public List<Book> getBooksByAuthor(String author){
        List<Book> books = bookRepository.findByAuthor(author);
        if(books.isEmpty()){
            throw new BusinessException("Book Not Found", HttpStatus.NOT_FOUND);
        }
        return books;
    }

    @Transactional
    public BookDTO.BookResponse createBook(BookDTO.BookCreateRequest request){
        bookRepository.findByIsbn(request.getIsbn())
                .ifPresent(
                        book -> {
                            throw new BusinessException("Book with this Isbn already Exist", HttpStatus.CONFLICT);
                        }
                );
        Book book = request.toEntity();
        Book savedBook = bookRepository.save(book);
        return new BookDTO.BookResponse(savedBook);
    }

    @Transactional
    public BookDTO.BookResponse updateBook(BookDTO.BookUpdateRequest request, Long id){
        Book book = bookRepository.findById(id).orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));

        book.setTitle(request.getTitle());
        //Transactional이 있으면 save()함수 안해도 적용이 됨
        return new BookDTO.BookResponse(book);
    }

    @Transactional
    public void deleteBook(Long id){
        Book book = bookRepository.findById(id).orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
        bookRepository.delete(book);
    }
}
