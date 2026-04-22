package com.taller.bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthorRequest {
    @NotBlank(message = "must not be blank")
    @Size(max = 120, message = "must not exceed 120 characters")
    private String name;

    @Size(max = 1000, message = "must not exceed 1000 characters")
    private String biography;

    @Size(max = 120, message = "must not exceed 120 characters")
    private String email;

    @Size(max = 30, message = "must not exceed 30 characters")
    private String phone;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBiography() { return biography; }
    public void setBiography(String biography) { this.biography = biography; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
