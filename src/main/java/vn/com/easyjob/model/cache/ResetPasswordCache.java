package vn.com.easyjob.model.cache;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode
public class ResetPasswordCache {
    String token;
    Timestamp expiryTime;
    String username;
}
