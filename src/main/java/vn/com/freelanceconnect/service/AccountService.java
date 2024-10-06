package vn.com.freelanceconnect.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.com.freelanceconnect.model.dto.TokenDTO;
import vn.com.freelanceconnect.model.entity.Account;
import vn.com.freelanceconnect.model.record.RegisterRecord;
import vn.com.freelanceconnect.model.record.SignInRecord;

public interface AccountService extends UserDetailsService {
    TokenDTO signIn(SignInRecord record);

    TokenDTO signUp(RegisterRecord record);

    Account findOne(String username);

    Account getAuthenticatedAccount();

    Boolean isSendMailForgetPassword(String email);
}
