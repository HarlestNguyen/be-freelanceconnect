package vn.com.freelanceconnect.service.impl;

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
import vn.com.freelanceconnect.model.dto.TokenDTO;
import vn.com.freelanceconnect.model.entity.Account;
import vn.com.freelanceconnect.model.entity.Profile;
import vn.com.freelanceconnect.exception.ErrorHandler;
import vn.com.freelanceconnect.base.IRepository;
import vn.com.freelanceconnect.jwt.JwtService;
import vn.com.freelanceconnect.model.record.RegisterRecord;
import vn.com.freelanceconnect.model.record.SignInRecord;
import vn.com.freelanceconnect.repository.AccountRepository;
import vn.com.freelanceconnect.repository.ProfileRepository;
import vn.com.freelanceconnect.base.BaseAbstractService;
import vn.com.freelanceconnect.service.AccountService;

import java.util.Optional;

@Service
public class AccountServiceImpl extends BaseAbstractService<Account, Integer> implements AccountService {
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

    @Override
    protected IRepository<Account, Integer> getRepository() {
        return accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isPresent() && !account.get().getIsDeleted())
            return account.get();
        else throw new ErrorHandler(HttpStatus.UNAUTHORIZED, "Account not exist");
    }


    @Override
    public TokenDTO signIn(SignInRecord record) {
        String accessToken = null;
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(record.username(), record.password()));
        if (authentication.isAuthenticated()) {
            Account account = new Account();
            account.setUsername(authentication.getName());
            accessToken = jwtService.generateToken(account);
        }
        //missing refresh token
        if (accessToken != null) {
            return new TokenDTO(accessToken);
        }
        return null;
    }

    @Override
    public Account signUp(RegisterRecord record) {
        Account account = new Account();
        profileRepository.findByEmail(record.email()).ifPresentOrElse(
                profile -> account.setProfile(profile),
                () -> {
                    Profile profile = new Profile();
                    profile.setEmail(record.email());
                    profile.setFullname(record.fullname());
                    account.setProfile(profileRepository.save(profile));
                }
        );
        account.setUsername(record.username());
        account.setPassword(passwordEncoder.encode(record.password()));
        return save(account);
    }


    @Override
    public Account findOne(String username) {
        return accountRepository.findByUsername(username).orElseThrow(
                () -> new ErrorHandler(HttpStatus.NOT_FOUND, "Entity not found")
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
        return null;
    }
}
