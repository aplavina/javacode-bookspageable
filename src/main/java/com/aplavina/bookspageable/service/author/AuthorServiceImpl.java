package com.aplavina.bookspageable.service.author;

import com.aplavina.bookspageable.model.Author;
import com.aplavina.bookspageable.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public boolean existsById(long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public Author findById(long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with id " + id + " not found"));
    }
}
