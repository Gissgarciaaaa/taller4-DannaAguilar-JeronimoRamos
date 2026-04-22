package com.taller.bookstore.dto.response;

public class AuthorResponse {
    private Long id;
    private String name;
    private String biography;
    private String email;
    private String phone;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBiography() { return biography; }
    public void setBiography(String biography) { this.biography = biography; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
