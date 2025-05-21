package com.example.MySpringbootLab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="publishers")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    private Long id;

    @Column
    private String name;

    @Column
    private LocalDate establishedDate;

    @Column
    private String address;

    @OneToMany(mappedBy = "publisher" ,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books;
}
