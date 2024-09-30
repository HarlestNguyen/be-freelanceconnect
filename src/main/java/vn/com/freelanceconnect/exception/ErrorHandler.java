package vn.com.freelanceconnect.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorHandler extends RuntimeException {

    private HttpStatus status;

    public ErrorHandler(HttpStatus status, String msg) {
        super(msg);
        this.status = status;
    }
}
