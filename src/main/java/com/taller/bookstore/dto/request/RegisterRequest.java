package com.taller.bookstore.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank(message = "must not be blank")
    private String name;

    @NotBlank(message = "must not be blank")
    @Email(message = "must be a well-formed email address")
    private String email;

    @NotBlank(message = "must not be blank")
    @Size(min = 8, message = "must have at least 8 characters")
    private String password;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
