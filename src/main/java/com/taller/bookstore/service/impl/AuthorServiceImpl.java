package com.taller.bookstore.service.impl;

import com.taller.bookstore.dto.request.AuthorRequest;
import com.taller.bookstore.dto.response.AuthorResponse;
import com.taller.bookstore.dto.response.BookResponse;
import com.taller.bookstore.entity.Author;
import com.taller.bookstore.exception.custom.AuthorHasBooksException;
import com.taller.bookstore.exception.custom.ResourceNotFoundException;
import com.taller.bookstore.mapper.AuthorMapper;
import com.taller.bookstore.mapper.BookMapper;
import com.taller.bookstore.repository.AuthorRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.taller.bookstore.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper, BookMapper bookMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
        this.bookMapper = bookMapper;
    }

    @Override
    public AuthorResponse create(AuthorRequest request) {
        return authorMapper.toResponse(authorRepository.save(authorMapper.toEntity(request)));
    }

    @Override
    public List<AuthorResponse> findAll() {
        return authorRepository.findAll().stream().map(authorMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public AuthorResponse findById(Long id) {
        return authorMapper.toResponse(getAuthor(id));
    }

    @Override
    public AuthorResponse update(Long id, AuthorRequest request) {
        Author author = getAuthor(id);
        authorMapper.updateEntity(author, request);
        return authorMapper.toResponse(authorRepository.save(author));
    }

    @Override
    public void delete(Long id) {
        Author author = getAuthor(id);
        if (!author.getBooks().isEmpty()) {
            throw new AuthorHasBooksException("author with id " + id + " has books associated");
        }
        authorRepository.delete(author);
    }

    @Override
    public List<BookResponse> findBooksByAuthor(Long id) {
        Author author = getAuthor(id);
        return author.getBooks().stream().map(bookMapper::toResponse).collect(Collectors.toList());
    }

    private Author getAuthor(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("author with id " + id + " not found"));
    }
}
