package vn.edu.likelion.group3.hackathon.controller;

import vn.edu.likelion.group3.hackathon.entity.Account;
import vn.edu.likelion.group3.hackathon.generic.GenericController;
import vn.edu.likelion.group3.hackathon.generic.IService;
import vn.edu.likelion.group3.hackathon.service.AccountService;
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
