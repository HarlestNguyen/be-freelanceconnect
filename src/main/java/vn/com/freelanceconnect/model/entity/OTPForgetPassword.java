package vn.com.freelanceconnect.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.IdGeneratorType;

import java.sql.Date;

@Entity
@Data
@Table(name = "tbl_otp_forget_password")
public class OTPForgetPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date expiryDate;
    private String otp;
    private String account;
}
