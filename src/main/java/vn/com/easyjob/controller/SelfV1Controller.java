package vn.com.easyjob.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.easyjob.service.Auth.AccountService;

@Tag(name = "self-v1")
@RestController
@RequestMapping("/api/v1/self")
public class SelfV1Controller {

}
