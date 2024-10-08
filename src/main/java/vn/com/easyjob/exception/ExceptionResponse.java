package vn.com.easyjob.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExceptionResponse {
    private final String status = "error";
    private String timestamp;
    private Object message;

    public ExceptionResponse(Object message) {
        this.timestamp = LocalDateTime.now().toString();
        this.message = message;
    }
}
