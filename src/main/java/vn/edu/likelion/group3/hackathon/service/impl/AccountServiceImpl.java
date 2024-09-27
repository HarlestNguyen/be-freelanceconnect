package vn.edu.likelion.group3.hackathon.service.impl;

import vn.edu.likelion.group3.hackathon.dto.TokenDTO;
import vn.edu.likelion.group3.hackathon.entity.Account;
import vn.edu.likelion.group3.hackathon.exception.ErrorHandler;
import vn.edu.likelion.group3.hackathon.generic.IRepository;
import vn.edu.likelion.group3.hackathon.jwt.JwtService;
import vn.edu.likelion.group3.hackathon.record.RegisterRecord;
import vn.edu.likelion.group3.hackathon.record.SignInRecord;
import vn.edu.likelion.group3.hackathon.repository.AccountRepository;
import vn.edu.likelion.group3.hackathon.service.AbstractService;
import vn.edu.likelion.group3.hackathon.service.AccountService;
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
