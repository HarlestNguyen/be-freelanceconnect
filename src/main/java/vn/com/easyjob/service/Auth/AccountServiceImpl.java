package vn.com.easyjob.service.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseAbstractService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.exception.ErrorHandler;
import vn.com.easyjob.jwt.JwtService;
import vn.com.easyjob.model.dto.TokenDTO;
import vn.com.easyjob.model.entity.Account;
import vn.com.easyjob.model.entity.Profile;
import vn.com.easyjob.model.entity.Role;
import vn.com.easyjob.model.record.ChangePasswordRecord;
import vn.com.easyjob.model.record.RegisterRecord;
import vn.com.easyjob.model.record.SignInRecord;
import vn.com.easyjob.repository.AccountRepository;
import vn.com.easyjob.repository.ProfileRepository;
import vn.com.easyjob.service.mail.MailService;
import vn.com.easyjob.util.EmailSubjectEnum;
import vn.com.easyjob.util.TypeMailEnum;

@Service
public class AccountServiceImpl extends BaseAbstractService<Account, Long> implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private MailService mailService;

    @Override
    protected IRepository<Account, Long> getRepository() {
        return accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Account> account = accountRepository.findByEmail(username);
//        if (account.isPresent() && !account.get().getIsDeleted())
//            return account.get();
//        else throw new ErrorHandler(HttpStatus.UNAUTHORIZED, "Account not exist");
        return findOne(username);
    }


    @Override
    public TokenDTO signIn(SignInRecord record) {
        String accessToken = null;
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(record.username(), record.password()));
        if (authentication.isAuthenticated()) {
            Account account = new Account();
            account.setEmail(authentication.getName());
            accessToken = jwtService.generateToken(account);
        }
        //missing refresh token
        if (accessToken != null) {
            return new TokenDTO(accessToken);
        }
        return null;
    }

    @Override
    public TokenDTO signUp(RegisterRecord record) {
        if (record.role().getId() == 1L) {
            return null;
        }
        Account account = new Account();
        Profile profile = new Profile();
        profile.setFullname(record.fullname().toUpperCase());
        account.setProfile(profileRepository.save(profile));

        Role role = new Role();
        role.setId(record.role().getId());

        account.setEmail(record.email());
        account.setPassword(passwordEncoder.encode(record.password()));
        account.setRole(role);

        Account result = save(account);
        String accessToken = jwtService.generateToken(result);
        if (accessToken != null) {
            return new TokenDTO(accessToken);
        }
        return null;
    }

    public Account findOne(String email) {
        return accountRepository.findByEmail(email)
                .filter(account -> !account.getIsDeleted())
                .orElseThrow(
                        () -> new ErrorHandler(HttpStatus.NOT_FOUND, "Account not found")
                );
    }

    @Override
    public Account getAuthenticatedAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            return findOne(authentication.getName());
        }
        return null;
    }

    @Override
    public Boolean isSendMailForgetPassword(String email) {
        findOne(email);
        return mailService.sendWithTemplate(email, null, EmailSubjectEnum.OTP, TypeMailEnum.OTP);

    }

    @Override
    public Boolean isChangePassword(ChangePasswordRecord changePasswordRecord) {
        Account account = getAuthenticatedAccount();
        if (passwordEncoder.matches(changePasswordRecord.oldPassword(), account.getPassword())) {
            account.setPassword(passwordEncoder.encode(changePasswordRecord.newPassword()));
            save(account);
            return true;
        } else {
            return false;
        }
    }

}
