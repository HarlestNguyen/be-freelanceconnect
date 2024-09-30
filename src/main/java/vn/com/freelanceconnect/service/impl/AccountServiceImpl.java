package vn.com.freelanceconnect.service.impl;

import vn.com.freelanceconnect.dto.TokenDTO;
import vn.com.freelanceconnect.jwt.JwtService;
import vn.com.freelanceconnect.repository.AccountRepository;
import vn.com.freelanceconnect.entity.Account;
import vn.com.freelanceconnect.exception.ErrorHandler;
import vn.com.freelanceconnect.generic.IRepository;
import vn.com.freelanceconnect.record.RegisterRecord;
import vn.com.freelanceconnect.record.SignInRecord;
import vn.com.freelanceconnect.service.AbstractService;
import vn.com.freelanceconnect.service.AccountService;
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

import java.util.Optional;

@Service
public class AccountServiceImpl extends AbstractService<Account, Integer> implements AccountService {
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
        account.setUsername(record.username());
        account.setPassword(passwordEncoder.encode(record.password()));
        account.setIsDeleted(false);
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
