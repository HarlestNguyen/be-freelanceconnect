package vn.com.easyjob.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorHandler extends RuntimeException {

    private HttpStatus status;

    public ErrorHandler(HttpStatus status, String msg) {
        super(msg);
        this.status = status;
    }
}
