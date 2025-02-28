package com.aplavina.bookspageable.service.book;

import com.aplavina.bookspageable.dto.BookDto;
import com.aplavina.bookspageable.exception.MissingFieldException;
import com.aplavina.bookspageable.mapper.BookMapper;
import com.aplavina.bookspageable.model.Book;
import com.aplavina.bookspageable.repository.BookRepository;
import com.aplavina.bookspageable.service.author.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    @Override
    public Page<BookDto> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::toBookDto);
    }

    @Override
    public BookDto getById(long bookId) {
        return bookMapper.toBookDto(
                bookRepository
                        .findById(bookId)
                        .orElseThrow(() -> new EntityNotFoundException("Book with id " + bookId + " not found"))
        );
    }

    @Override
    public BookDto create(BookDto bookDto) {
        if (!authorService.existsById(bookDto.getAuthorId())) {
            throw new EntityNotFoundException("Author with id " + bookDto.getAuthorId() + " not found");
        }
        return bookMapper.toBookDto(bookRepository.save(bookMapper.toEntity(bookDto)));
    }

    @Override
    public BookDto update(BookDto bookDto) {
        if (bookDto.getId() == null) {
            throw new MissingFieldException("id is required");
        }
        Book book = bookRepository.findById(bookDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Book with id " + bookDto.getId() + " not found"));
        book.setName(bookDto.getName());
        book.setAuthor(authorService.findById(bookDto.getAuthorId()));
        return bookMapper.toBookDto(book);
    }

    @Override
    public void delete(long bookId) {
        bookRepository.deleteById(bookId);
    }
}
