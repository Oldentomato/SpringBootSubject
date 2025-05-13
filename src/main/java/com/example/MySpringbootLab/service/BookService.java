package com.example.MySpringbootLab.service;

import com.example.MySpringbootLab.entity.Book;
import com.example.MySpringbootLab.entity.BookDetail;
import com.example.MySpringbootLab.entity.dto.BookDTO;
import com.example.MySpringbootLab.exception.BusinessException;
import com.example.MySpringbootLab.repository.BookDetailRepository;
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
    private final BookDetailRepository bookDetailRepository;

    public List<BookDTO.Response> getAllBooks(){

        return bookRepository.findAll()
                .stream()
                .map(book -> BookDTO.Response.fromEntity(book))
                .toList();
    }

    public BookDTO.Response getBookById(Long id){
        Book book = bookRepository.findById(id).orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
        return BookDTO.Response.fromEntity(book);
    }

    public BookDTO.Response getBookByIsbn(String isbn){
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
        return BookDTO.Response.fromEntity(book);
    }

    public List<BookDTO.Response> getBooksByAuthor(String author){
        List<BookDTO.Response> books = bookRepository.findByAuthor(author).stream().map(book -> BookDTO.Response.fromEntity(book)).toList();
        if(books.isEmpty()){
            throw new BusinessException("Book Not Found", HttpStatus.NOT_FOUND);
        }
        return books;
    }

    @Transactional
    public BookDTO.Response createBook(BookDTO.Request request){
        bookRepository.findByIsbn(request.getIsbn())
                .ifPresent(
                        book -> {
                            throw new BusinessException("Book with this Isbn already Exist", HttpStatus.CONFLICT);
                        }
                );
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .build();

        if(request.getDetailRequest() != null){
            BookDetail bookDetail = BookDetail.builder()
                    .description(request.getDetailRequest().getDescription())
                    .language(request.getDetailRequest().getLanguage())
                    .pageCount(request.getDetailRequest().getPageCount())
                    .publisher(request.getDetailRequest().getPublisher())
                    .coverImageUrl(request.getDetailRequest().getCoverImageUrl())
                    .edition(request.getDetailRequest().getEdition())
                    .book(book)
                    .build();

            book.setBookDetail(bookDetail);
        }

        Book savedBook = bookRepository.save(book);
        return BookDTO.Response.fromEntity(savedBook);
    }

    @Transactional
    public BookDTO.Response updateBook(BookDTO.Request request, Long id){
        Book book = bookRepository.findById(id).orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));

        book.setTitle(request.getTitle());
        //Transactional이 있으면 save()함수 안해도 적용이 됨
        Book updatedBook = bookRepository.save(book);
        return BookDTO.Response.fromEntity(updatedBook);
    }

    @Transactional
    public void deleteBook(Long id){
        Book book = bookRepository.findById(id).orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
        bookRepository.delete(book);
    }

//    private boolean has
}
