package com.aplavina.bookspageable.repository;

import com.aplavina.bookspageable.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}