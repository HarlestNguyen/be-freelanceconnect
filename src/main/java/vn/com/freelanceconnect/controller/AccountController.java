package vn.com.freelanceconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.freelanceconnect.model.entity.Account;
import vn.com.freelanceconnect.base.IService;
import vn.com.freelanceconnect.service.AccountService;

@RestController
@RequestMapping("/api/self")
public class AccountController {
    @Autowired
    private AccountService accountService;
}
