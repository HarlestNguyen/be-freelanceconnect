package vn.com.easyjob.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseListPagination<T>{
    String message;
    int statusCode;
    List<T> data;
    int total;
    int page;
    int limit;
    Date date;

    // trả về 1 array object
    public ResponseListPagination(String message, int statusCode, List<T> data, int total, int page, int limit, Date date) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
        this.total = total;
        this.page = page;
        this.limit = limit;
        this.date = date;
    }
}
