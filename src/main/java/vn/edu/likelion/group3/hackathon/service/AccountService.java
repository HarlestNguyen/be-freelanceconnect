package vn.edu.likelion.group3.hackathon.service;

import vn.edu.likelion.group3.hackathon.dto.TokenDTO;
import vn.edu.likelion.group3.hackathon.entity.Account;
import vn.edu.likelion.group3.hackathon.generic.IService;
import vn.edu.likelion.group3.hackathon.record.RegisterRecord;
import vn.edu.likelion.group3.hackathon.record.SignInRecord;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends IService<Account, Integer>, UserDetailsService {
    TokenDTO signIn(SignInRecord record);

    Account signUp(RegisterRecord record);

    Account findOne(String username);

    Account getAuthenticatedAccount();

    Boolean isSendMailForgetPassword(String email);
}
