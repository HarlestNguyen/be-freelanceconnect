package vn.com.easyjob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.easyjob.service.AccountService;

@RestController
@RequestMapping("/api/self")
public class AccountController {
    @Autowired
    private AccountService accountService;
}
