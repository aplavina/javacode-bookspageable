package com.aplavina.bookspageable.controller;

import com.aplavina.bookspageable.dto.BookDto;
import com.aplavina.bookspageable.service.book.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public Page<BookDto> getAll(Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @GetMapping("/{bookId}")
    public BookDto getById(@PathVariable long bookId) {
        return bookService.getById(bookId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto create(@RequestBody @Valid BookDto bookDto) {
        return bookService.create(bookDto);
    }

    @PutMapping
    @Transactional
    public BookDto update(@RequestBody @Valid BookDto bookDto) {
        return bookService.update(bookDto);
    }

    @DeleteMapping("/{bookId}")
    public void delete(@PathVariable long bookId) {
        bookService.delete(bookId);
    }
}
