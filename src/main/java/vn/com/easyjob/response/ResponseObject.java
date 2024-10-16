package vn.com.easyjob.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseObject<T> {
    String message;
    int statusCode;
    T data;
    Date date;

    // trả về 1 array object
    public ResponseObject(String message, int statusCode, T data, Date date) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
        this.date = date;
    }

}
