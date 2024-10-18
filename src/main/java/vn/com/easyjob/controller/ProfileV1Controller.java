package vn.com.easyjob.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.easyjob.service.auth.ProfileService;

@Tag(name = "profile")
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileV1Controller {
    @Autowired
    private ProfileService profileService;


}
