package com.aplavina.bookspageable.mapper;

import com.aplavina.bookspageable.dto.BookDto;
import com.aplavina.bookspageable.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {
    @Mapping(source = "authorId", target = "author.id")
    Book toEntity(BookDto bookDto);

    @Mapping(source = "author.id", target = "authorId")
    BookDto toBookDto(Book book);
}