package vn.com.easyjob.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OTPDTO {
    private String expiryDate;
    private String otp;
    private String account;
}
