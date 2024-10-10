package vn.com.easyjob.model.record;

import jakarta.validation.constraints.Pattern;

public record ChangePasswordRecord(
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{8,16}$",
                message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and be between 8 and 16 characters long."
        ) String oldPassword,
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{8,16}$",
                message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and be between 8 and 16 characters long."
        ) String newPassword
) {
}
