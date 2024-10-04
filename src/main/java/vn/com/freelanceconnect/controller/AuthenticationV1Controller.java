package vn.com.freelanceconnect.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.freelanceconnect.model.dto.ResponseDTO;
import vn.com.freelanceconnect.model.dto.TokenDTO;
import vn.com.freelanceconnect.exception.ErrorHandler;
import vn.com.freelanceconnect.exception.ExceptionResponse;
import vn.com.freelanceconnect.model.record.RegisterRecord;
import vn.com.freelanceconnect.model.record.SignInRecord;
import vn.com.freelanceconnect.service.AccountService;
import vn.com.freelanceconnect.util.AuthConstants;

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
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Register successfully, please check your email to verify account.", accountService.signUp(record)));
    }

    @PostMapping("/sign-out")
    @PreAuthorize(AuthConstants.ALL)
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> logout(HttpServletRequest request) {
//        JwtBlacklistToken blacklistToken = new JwtBlacklistToken();
//        blacklistToken.setToken(request.getHeader("Authorization").substring(7));
//        blacklistToken.setExpirationDate(new Timestamp(jwtService
//                .extractExpiration(request
//                        .getHeader("Authorization")
//                        .substring(7)).getTime()));
//        jwtBlacklistTokenRepository.save(blacklistToken);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Logout successfully."));
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
