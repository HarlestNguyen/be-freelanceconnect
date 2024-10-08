package vn.com.freelanceconnect.model.record;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SignInRecord(
        @Pattern(
                regexp = "[a-zA-Z0-9]{8,}",
                message = "Username must be at least 8 characters long and only contain letters, numbers")
        String username,
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{8,16}$",
                message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and be between 8 and 16 characters long."
        ) String password
) {
}
