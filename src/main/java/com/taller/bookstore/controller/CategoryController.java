package com.taller.bookstore.controller;

import com.taller.bookstore.dto.request.CategoryRequest;
import com.taller.bookstore.dto.response.ApiResponse;
import com.taller.bookstore.dto.response.BookResponse;
import com.taller.bookstore.dto.response.CategoryResponse;
import com.taller.bookstore.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> create(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Categoría creada correctamente", categoryService.create(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.success(200, "Categorías consultadas correctamente", categoryService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(200, "Categoría consultada correctamente", categoryService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(ApiResponse.success(200, "Categoría actualizada correctamente", categoryService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(200, "Categoría eliminada correctamente", null));
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<ApiResponse<List<BookResponse>>> findBooksByCategory(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(200, "Libros de la categoría consultados correctamente", categoryService.findBooksByCategory(id)));
    }
}
