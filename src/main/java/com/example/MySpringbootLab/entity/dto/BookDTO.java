package com.example.MySpringbootLab.entity.dto;

import com.example.MySpringbootLab.entity.Book;
import jakarta.validation.Valid;
import lombok.*;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class BookDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request{
        @NotBlank(message = "title is required")
        private String title;

        @NotBlank(message = "author is required")
        private String author;

        @NotBlank(message = "isbn is required")
        private String isbn;

        @NotBlank(message = "price is required")
        private int price;

        @NotBlank(message = "publisDate is required")
        private LocalDateTime publisDate;

        @Valid
        private BookDetailDTO detailRequest;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookDetailDTO{
        @NotBlank(message = "description is required")
        private String description;

        @NotBlank(message = "language is required")
        private String language;

        @NotBlank(message = "pageCount is required")
        private int pageCount;

        @NotBlank(message = "publisher is required")
        private String publisher;

        @NotBlank(message = "coverImageUrl is required")
        private String coverImageUrl;

        @NotBlank(message = "edition is required")
        private String edition;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private int price;
        private LocalDateTime publishDate;
        private BookDetailResponse detail;

        public static Response fromEntity(Book book){
            BookDetailResponse detailResponse = book.getBookDetail() != null
                    ? BookDetailResponse.builder()
                    .id(book.getBookDetail().getId())
                    .description(book.getBookDetail().getDescription())
                    .pageCount(book.getBookDetail().getPageCount())
                    .publisher(book.getBookDetail().getPublisher())
                    .coverImageUrl(book.getBookDetail().getCoverImageUrl())
                    .edition(book.getBookDetail().getEdition())
                    .build()
                    :null;

            return Response.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .isbn(book.getIsbn())
                    .price(book.getPrice())
                    .publishDate(book.getPublishDate())
                    .detail(detailResponse)
                    .build();

        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookDetailResponse{
        private Long id;
        private String description;
        private int pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;
    }

//    @Getter
//    @Setter
//    public static class BookCreateRequest{
//        @NotBlank(message = "title is required")
//        private String title;
//
//        @NotBlank(message = "author is required")
//        private String author;
//
//        @NotBlank(message = "isbn is required")
//        private String isbn;
//
//        @NotBlank(message = "price is required")
//        private int price;
//
//
//        public Book toEntity(){
//            Book book = new Book();
//            book.setTitle(this.title);
//            book.setAuthor(this.author);
//            book.setIsbn(this.isbn);
//            book.setPrice(this.price);
//
//            return book;
//        }
//    }

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
