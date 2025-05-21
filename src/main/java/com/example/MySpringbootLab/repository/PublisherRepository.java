package com.example.MySpringbootLab.repository;

import com.example.MySpringbootLab.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Optional<Publisher> findByName(String name);
    @Query("SELECT b FROM Publisher b LEFT JOIN FETCH b.books WHERE b.id = :id")
    Optional<Publisher> findByIdWithBooks(@Param("id") Long id);
    boolean existsByName(String name);
}
