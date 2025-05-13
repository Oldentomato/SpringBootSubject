package com.example.MySpringbootLab.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BookDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_detail_id")
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private int pageCount;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String coverImageUrl;

    @Column(nullable = false)
    private String edition;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_detail", unique = true)
    private Book book;


}
