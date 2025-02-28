package com.aplavina.bookspageable.service.book;

import com.aplavina.bookspageable.dto.BookDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

public interface BookService {
    Page<BookDto> getAll(Pageable pageable);

    BookDto getById(long bookId);

    BookDto create(@RequestBody @Valid BookDto bookDto);

    BookDto update(BookDto bookDto);

    void delete(long bookId);
}
