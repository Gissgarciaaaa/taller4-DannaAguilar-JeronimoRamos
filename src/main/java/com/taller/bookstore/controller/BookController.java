package com.taller.bookstore.controller;

import com.taller.bookstore.dto.request.BookRequest;
import com.taller.bookstore.dto.response.ApiResponse;
import com.taller.bookstore.dto.response.BookResponse;
import com.taller.bookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>> create(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Libro creado correctamente", bookService.create(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<BookResponse>>> findAll(
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(200, "Libros consultados correctamente", bookService.findAll(authorId, categoryId, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(200, "Libro consultado correctamente", bookService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(ApiResponse.success(200, "Libro actualizado correctamente", bookService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(200, "Libro eliminado correctamente", null));
    }
}
