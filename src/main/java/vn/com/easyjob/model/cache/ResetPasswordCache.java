package vn.com.easyjob.model.cache;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResetPasswordCache {
    String token;
    Timestamp expiryTime;
    String username;
}
