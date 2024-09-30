package vn.com.freelanceconnect.controller;

import vn.com.freelanceconnect.entity.Account;
import vn.com.freelanceconnect.generic.GenericController;
import vn.com.freelanceconnect.generic.IService;
import vn.com.freelanceconnect.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/self")
public class AccountController extends GenericController<Account, Integer> {
    @Autowired
    private AccountService accountService;

    @Override
    public IService<Account, Integer> getService() {
        return accountService;
    }

}
