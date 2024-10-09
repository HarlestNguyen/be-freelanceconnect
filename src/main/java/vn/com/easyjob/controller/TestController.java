package vn.com.easyjob.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.com.easyjob.service.MailService;
import vn.com.easyjob.util.EmailSubjectEnum;
import vn.com.easyjob.util.TypeMailEnum;

import java.util.UUID;

@RestController
@RequestMapping("/test")
@Hidden
public class TestController {
    private final static String testToken = UUID.randomUUID().toString();
    @Autowired
    private MailService mailService;

    @GetMapping
    public void test(@RequestParam String email, final HttpServletRequest request) {
        mailService.sendWithTemplate("nguyenmanhkhang2002@gmail.com", applicationUrl(request) + "/test/verifyEmail?token=" + testToken, EmailSubjectEnum.BASIC, TypeMailEnum.VERIFY_LINK);
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) {
        if (token.equals(testToken)) {
            return "This account has already been verified, please, login.";
        }
        return "Invalid verification token";
    }

}
