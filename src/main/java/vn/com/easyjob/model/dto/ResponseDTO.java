package vn.com.easyjob.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseDTO {
    private final String status = "success";
    private String timestamp;
    private String message;
    private Object data;

    public ResponseDTO(Object data) {
        this.timestamp = LocalDateTime.now().toString();
        this.message = null;
        this.data = data;
    }

    public ResponseDTO(String message) {
        this.timestamp = LocalDateTime.now().toString();
        this.message = message;
        this.data = null;
    }

    public ResponseDTO(String message, Object data) {
        this.timestamp = LocalDateTime.now().toString();
        this.message = message;
        this.data = data;
    }
}
