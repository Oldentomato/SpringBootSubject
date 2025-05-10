package com.example.MySpringbootLab.entity.dto;

import com.example.MySpringbootLab.entity.Book;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class BookDTO {

    @Getter
    @Setter
    public static class BookCreateRequest{
        @NotBlank(message = "title is required")
        private String title;

        @NotBlank(message = "author is required")
        private String author;

        @NotBlank(message = "isbn is required")
        private String isbn;

        @NotBlank(message = "price is required")
        private int price;


        public Book toEntity(){
            Book book = new Book();
            book.setTitle(this.title);
            book.setAuthor(this.author);
            book.setIsbn(this.isbn);
            book.setPrice(this.price);

            return book;
        }
    }

    @Getter
    @Setter
    public static class BookUpdateRequest{
        @NotBlank(message = "title is required")
        private String title;

        @NotBlank(message = "author is required")
        private String author;

        @NotBlank(message = "price is required")
        private int price;
    }

    @Getter
    public static class BookResponse{
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private int price ;
        private LocalDateTime publishDate;

        public BookResponse(Book book){
            this.id = book.getId();
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.isbn = book.getIsbn();
            this.price = book.getPrice();
            this.publishDate = book.getPublishDate();
        }
    }
}
