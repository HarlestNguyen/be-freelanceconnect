package vn.com.easyjob.service.auth;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.com.easyjob.base.IService;
import vn.com.easyjob.model.dto.TokenDTO;
import vn.com.easyjob.model.entity.Account;
import vn.com.easyjob.model.record.ChangePasswordRecord;
import vn.com.easyjob.model.record.RegisterRecord;
import vn.com.easyjob.model.record.SignInRecord;
import vn.com.easyjob.util.RoleEnum;

public interface AccountService extends UserDetailsService, IService<Account, Long> {
    TokenDTO signIn(SignInRecord record);

    TokenDTO signUp(RegisterRecord record);

    Account findOne(String username);

    Account getAuthenticatedAccount();

    Boolean isSendMailForgetPassword(String email);

    Boolean isChangePassword(ChangePasswordRecord changePasswordRecord);

    TokenDTO outboundAuthenticate(String code, RoleEnum role);

    Boolean validateTokenAndChangePassword(String token, String password);
}
