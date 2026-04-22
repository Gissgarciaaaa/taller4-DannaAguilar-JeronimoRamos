package com.taller.bookstore.dto.response;

import java.time.Instant;
import java.util.List;

public class ApiErrorResponse {
    private String status;
    private int code;
    private String message;
    private List<String> errors;
    private Instant timestamp;
    private String path;

    public ApiErrorResponse() {}

    public ApiErrorResponse(String status, int code, String message, List<String> errors, Instant timestamp, String path) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.errors = errors;
        this.timestamp = timestamp;
        this.path = path;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}
