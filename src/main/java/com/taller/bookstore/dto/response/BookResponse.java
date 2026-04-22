package com.taller.bookstore.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class BookResponse {
    private Long id;
    private String title;
    private String isbn;
    private BigDecimal price;
    private Integer stock;
    private String description;
    private AuthorResponse author;
    private List<CategoryResponse> categories;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public AuthorResponse getAuthor() { return author; }
    public void setAuthor(AuthorResponse author) { this.author = author; }
    public List<CategoryResponse> getCategories() { return categories; }
    public void setCategories(List<CategoryResponse> categories) { this.categories = categories; }
}
