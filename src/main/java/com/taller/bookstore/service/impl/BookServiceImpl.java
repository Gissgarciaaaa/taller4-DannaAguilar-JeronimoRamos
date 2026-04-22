package com.taller.bookstore.service.impl;

import com.taller.bookstore.dto.request.BookRequest;
import com.taller.bookstore.dto.response.BookResponse;
import com.taller.bookstore.entity.Author;
import com.taller.bookstore.entity.Book;
import com.taller.bookstore.entity.Category;
import com.taller.bookstore.exception.custom.DuplicateResourceException;
import com.taller.bookstore.exception.custom.ResourceNotFoundException;
import com.taller.bookstore.mapper.BookMapper;
import com.taller.bookstore.repository.AuthorRepository;
import com.taller.bookstore.repository.BookRepository;
import com.taller.bookstore.repository.CategoryRepository;
import com.taller.bookstore.service.BookService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           CategoryRepository categoryRepository,
                           BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookResponse create(BookRequest request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new DuplicateResourceException("isbn already exists: " + request.getIsbn());
        }

        Book book = bookMapper.toEntity(request);
        assignRelations(book, request);
        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Override
    public Page<BookResponse> findAll(Long authorId, Long categoryId, Pageable pageable) {
        Page<Book> page;
        if (authorId != null) {
            page = bookRepository.findByAuthorId(authorId, pageable);
        } else if (categoryId != null) {
            page = bookRepository.findByCategoriesId(categoryId, pageable);
        } else {
            page = bookRepository.findAll(pageable);
        }
        return page.map(bookMapper::toResponse);
    }

    @Override
    public BookResponse findById(Long id) {
        return bookMapper.toResponse(getBook(id));
    }

    @Override
    public BookResponse update(Long id, BookRequest request) {
        Book book = getBook(id);
        if (!book.getIsbn().equals(request.getIsbn()) && bookRepository.existsByIsbn(request.getIsbn())) {
            throw new DuplicateResourceException("isbn already exists: " + request.getIsbn());
        }
        bookMapper.updateEntity(book, request);
        assignRelations(book, request);
        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Override
    public void delete(Long id) {
        bookRepository.delete(getBook(id));
    }

    private Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("book with id " + id + " not found"));
    }

    private void assignRelations(Book book, BookRequest request) {
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("author with id " + request.getAuthorId() + " not found"));
        List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());
        if (categories.size() != request.getCategoryIds().size()) {
            throw new ResourceNotFoundException("one or more categories were not found");
        }
        book.setAuthor(author);
        book.setCategories(categories);
    }
}
