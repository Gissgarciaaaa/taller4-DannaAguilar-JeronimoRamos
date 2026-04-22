package com.taller.bookstore.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public class BookRequest {
    @NotBlank(message = "must not be blank")
    @Size(max = 200, message = "must not exceed 200 characters")
    private String title;

    @NotBlank(message = "must not be blank")
    private String isbn;

    @NotNull(message = "must not be null")
    @DecimalMin(value = "0.01", message = "must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "must not be null")
    @Min(value = 0, message = "must be greater than or equal to 0")
    private Integer stock;

    private String description;

    @NotNull(message = "must not be null")
    private Long authorId;

    @NotNull(message = "must not be null")
    private List<Long> categoryIds;

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
    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
    public List<Long> getCategoryIds() { return categoryIds; }
    public void setCategoryIds(List<Long> categoryIds) { this.categoryIds = categoryIds; }
}
