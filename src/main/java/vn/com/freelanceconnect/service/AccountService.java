package vn.com.freelanceconnect.service;

import vn.com.freelanceconnect.dto.TokenDTO;
import vn.com.freelanceconnect.generic.IService;
import vn.com.freelanceconnect.entity.Account;
import vn.com.freelanceconnect.record.RegisterRecord;
import vn.com.freelanceconnect.record.SignInRecord;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends IService<Account, Integer>, UserDetailsService {
    TokenDTO signIn(SignInRecord record);

    Account signUp(RegisterRecord record);

    Account findOne(String username);

    Account getAuthenticatedAccount();

    Boolean isSendMailForgetPassword(String email);
}
