package com.aplavina.bookspageable.service.book;

import com.aplavina.bookspageable.dto.BookDto;
import com.aplavina.bookspageable.exception.MissingFieldException;
import com.aplavina.bookspageable.mapper.BookMapperImpl;
import com.aplavina.bookspageable.model.Book;
import com.aplavina.bookspageable.repository.BookRepository;
import com.aplavina.bookspageable.service.author.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Spy
    private BookMapperImpl bookMapper;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorService authorService;
    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setName("Test Book");

        bookDto = new BookDto(1L, "Test Book", 1L);
    }

    @Test
    void getAllWithSuccess() {
        Pageable pageable = PageRequest.of(0, 10);
        when(bookRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(book)));

        Page<BookDto> result = bookService.getAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Test Book", result.getContent().get(0).getName());
    }

    @Test
    void getByIdWithSuccess() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookDto result = bookService.getById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getByIdWhenNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.getById(1L));
    }

    @Test
    void createWithSuccess() {
        when(authorService.existsById(1L)).thenReturn(true);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDto result = bookService.create(bookDto);
        assertNotNull(result);
        assertEquals("Test Book", result.getName());
    }

    @Test
    void createWhenAuthorDoesNotExist() {
        when(authorService.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> bookService.create(bookDto));
    }

    @Test
    void updateWithSuccess() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(authorService.findById(1L)).thenReturn(book.getAuthor());

        BookDto result = bookService.update(bookDto);
        assertNotNull(result);
        assertEquals("Test Book", result.getName());
    }

    @Test
    void updateWhenIdIsMissing() {
        bookDto.setId(null);
        assertThrows(MissingFieldException.class, () -> bookService.update(bookDto));
    }

    @Test
    void updateWhenBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> bookService.update(bookDto));
    }

    @Test
    void deleteWithSuccess() {
        doNothing().when(bookRepository).deleteById(1L);
        bookService.delete(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }
}