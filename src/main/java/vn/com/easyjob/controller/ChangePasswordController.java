package vn.com.easyjob.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.com.easyjob.service.auth.AccountService;

@Controller
public class ChangePasswordController {
    private static final Logger log = LoggerFactory.getLogger(ChangePasswordController.class);
    @Autowired
    private AccountService accountService;

    @GetMapping("/change-password")
    public String showChangePasswordPage(@RequestParam(value = "token") String token, Model model) {
        model.addAttribute("token", token);
        return "user/change-password"; //map to file in src/main/resources/templates/
    }

    @PostMapping("/change-password")
    @Hidden
    @ResponseBody
    public String changePassword(@RequestParam String reNewPassword, @RequestParam String newPassword,
                                 @RequestParam String token) {
        log.warn(token);
        log.warn(reNewPassword);
        if (reNewPassword.equals(newPassword)) {
            if (accountService.validateTokenAndChangePassword(token, reNewPassword)) {
                return "Password changed successfully!";
            }else
                return "Password changed failed!";
        }
        return "Password doesn't match!";
    }
}
