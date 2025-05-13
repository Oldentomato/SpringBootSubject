package com.example.MySpringbootLab.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="books")
@Getter @Setter
@Builder
@DynamicUpdate
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime publishDate = LocalDateTime.now();

    // mappedBy는 Detail의 Book의 변수명을 쓰면 됨
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL)
    private BookDetail bookDetail;

}
