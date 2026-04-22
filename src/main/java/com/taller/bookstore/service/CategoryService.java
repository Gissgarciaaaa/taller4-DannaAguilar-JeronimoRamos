package com.taller.bookstore.service;

import com.taller.bookstore.dto.request.CategoryRequest;
import com.taller.bookstore.dto.response.BookResponse;
import com.taller.bookstore.dto.response.CategoryResponse;
import java.util.List;

public interface CategoryService {
    CategoryResponse create(CategoryRequest request);
    List<CategoryResponse> findAll();
    CategoryResponse findById(Long id);
    CategoryResponse update(Long id, CategoryRequest request);
    void delete(Long id);
    List<BookResponse> findBooksByCategory(Long id);
}
