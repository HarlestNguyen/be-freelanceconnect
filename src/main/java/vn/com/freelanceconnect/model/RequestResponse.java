package vn.com.freelanceconnect.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestResponse {
    private final String status = "success";
    private String timestamp;
    private String message;
    private Object data;

    public RequestResponse(Object data) {
        this.timestamp = LocalDateTime.now().toString();
        this.message = null;
        this.data = data;
    }

    public RequestResponse(String message) {
        this.timestamp = LocalDateTime.now().toString();
        this.message = message;
        this.data = null;
    }

    public RequestResponse(String message, Object data) {
        this.timestamp = LocalDateTime.now().toString();
        this.message = message;
        this.data = data;
    }
}
