package com.taller.bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryRequest {
    @NotBlank(message = "must not be blank")
    @Size(max = 100, message = "must not exceed 100 characters")
    private String name;

    @Size(max = 500, message = "must not exceed 500 characters")
    private String description;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
