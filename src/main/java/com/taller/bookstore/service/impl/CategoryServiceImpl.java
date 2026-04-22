package com.taller.bookstore.service.impl;

import com.taller.bookstore.dto.request.CategoryRequest;
import com.taller.bookstore.dto.response.BookResponse;
import com.taller.bookstore.dto.response.CategoryResponse;
import com.taller.bookstore.entity.Category;
import com.taller.bookstore.exception.custom.DuplicateResourceException;
import com.taller.bookstore.exception.custom.ResourceNotFoundException;
import com.taller.bookstore.mapper.BookMapper;
import com.taller.bookstore.mapper.CategoryMapper;
import com.taller.bookstore.repository.CategoryRepository;
import com.taller.bookstore.service.CategoryService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final BookMapper bookMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, BookMapper bookMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.bookMapper = bookMapper;
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        categoryRepository.findByNameIgnoreCase(request.getName()).ifPresent(c -> {
            throw new DuplicateResourceException("category already exists: " + request.getName());
        });
        return categoryMapper.toResponse(categoryRepository.save(categoryMapper.toEntity(request)));
    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse findById(Long id) {
        return categoryMapper.toResponse(getCategory(id));
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = getCategory(id);
        categoryMapper.updateEntity(category, request);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public void delete(Long id) {
        categoryRepository.delete(getCategory(id));
    }

    @Override
    public List<BookResponse> findBooksByCategory(Long id) {
        Category category = getCategory(id);
        return category.getBooks().stream().map(bookMapper::toResponse).collect(Collectors.toList());
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category with id " + id + " not found"));
    }
}
