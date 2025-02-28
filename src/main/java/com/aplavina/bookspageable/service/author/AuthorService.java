package com.aplavina.bookspageable.service.author;

import com.aplavina.bookspageable.model.Author;


public interface AuthorService {
    boolean existsById(long id);

    Author findById(long id);
}
