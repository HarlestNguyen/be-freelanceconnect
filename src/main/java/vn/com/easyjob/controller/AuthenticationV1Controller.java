package vn.com.easyjob.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.easyjob.exception.ErrorHandler;
import vn.com.easyjob.exception.ExceptionResponse;
import vn.com.easyjob.model.dto.ResponseDTO;
import vn.com.easyjob.model.dto.TokenDTO;
import vn.com.easyjob.model.record.ChangePasswordRecord;
import vn.com.easyjob.model.record.RegisterRecord;
import vn.com.easyjob.model.record.SignInRecord;
import vn.com.easyjob.service.Auth.AccountService;
import vn.com.easyjob.util.AuthConstants;

@Tag(name = "auth-v1")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationV1Controller {
    @Autowired
    private AccountService accountService;

    @PostMapping("/sign-in")
    @PreAuthorize(AuthConstants.NONE)
    public ResponseEntity<?> login(@RequestBody SignInRecord loginRecord) {
        TokenDTO dto = accountService.signIn(loginRecord);
        if (dto != null)
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Sign in successful", dto));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse("Invalid username or password"));
    }

    @PostMapping("/sign-up")
    @PreAuthorize(AuthConstants.NONE)
    @Transactional
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRecord record) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Register successfully", accountService.signUp(record)));
    }

    @PostMapping("/sign-out")
    @PreAuthorize(AuthConstants.ALL)
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Logout successfully."));
    }

    @PostMapping("/change-password")
    @PreAuthorize(AuthConstants.ALL)
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRecord record) {
        if (accountService.isChangePassword(record)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Change password successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO("Old password is incorrect."));
        }
    }

    @PostMapping("/forget-password")
    @PreAuthorize(AuthConstants.NONE)
    public ResponseEntity<?> forgetPassword(@RequestParam String email) {
        if (accountService.isSendMailForgetPassword(email)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Please check your email to change your password."));
        } else {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send change password link.");
        }
    }
}
