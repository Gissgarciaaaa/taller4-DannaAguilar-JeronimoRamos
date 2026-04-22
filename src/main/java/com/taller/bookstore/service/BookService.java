package com.taller.bookstore.service;

import com.taller.bookstore.dto.request.BookRequest;
import com.taller.bookstore.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponse create(BookRequest request);
    Page<BookResponse> findAll(Long authorId, Long categoryId, Pageable pageable);
    BookResponse findById(Long id);
    BookResponse update(Long id, BookRequest request);
    void delete(Long id);
}
