package vn.com.easyjob.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.com.easyjob.model.dto.TokenDTO;
import vn.com.easyjob.model.entity.Account;
import vn.com.easyjob.model.record.RegisterRecord;
import vn.com.easyjob.model.record.SignInRecord;

public interface AccountService extends UserDetailsService {
    TokenDTO signIn(SignInRecord record);

    TokenDTO signUp(RegisterRecord record);

    Account findOne(String username);

    Account getAuthenticatedAccount();

    Boolean isSendMailForgetPassword(String email);
}
