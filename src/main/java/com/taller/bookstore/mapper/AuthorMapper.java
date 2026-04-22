package com.taller.bookstore.mapper;

import com.taller.bookstore.dto.request.AuthorRequest;
import com.taller.bookstore.dto.response.AuthorResponse;
import com.taller.bookstore.entity.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public Author toEntity(AuthorRequest request) {
        Author author = new Author();
        author.setName(request.getName());
        author.setBiography(request.getBiography());
        author.setEmail(request.getEmail());
        author.setPhone(request.getPhone());
        return author;
    }

    public void updateEntity(Author author, AuthorRequest request) {
        author.setName(request.getName());
        author.setBiography(request.getBiography());
        author.setEmail(request.getEmail());
        author.setPhone(request.getPhone());
    }

    public AuthorResponse toResponse(Author author) {
        AuthorResponse response = new AuthorResponse();
        response.setId(author.getId());
        response.setName(author.getName());
        response.setBiography(author.getBiography());
        response.setEmail(author.getEmail());
        response.setPhone(author.getPhone());
        return response;
    }
}
