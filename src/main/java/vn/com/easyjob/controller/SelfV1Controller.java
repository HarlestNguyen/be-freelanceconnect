package vn.com.easyjob.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.easyjob.model.dto.ResponseDTO;
import vn.com.easyjob.model.entity.Profile;
import vn.com.easyjob.model.record.ChangeInfoRecord;
import vn.com.easyjob.model.record.ChangePasswordRecord;
import vn.com.easyjob.model.record.VerificationRecord;
import vn.com.easyjob.service.auth.AccountService;
import vn.com.easyjob.service.auth.ProfileService;
import vn.com.easyjob.service.cloudiary.CloudinaryService;
import vn.com.easyjob.service.job.JobSkillService;
import vn.com.easyjob.util.AuthConstants;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Tag(name = "self")
@RestController
@PreAuthorize(AuthConstants.ALL)
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/api/v1/self")
public class SelfV1Controller {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private JobSkillService jobSkillService;

    @PostMapping("/change-password")
    @PreAuthorize(AuthConstants.ALL)
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRecord record) {
        if (accountService.isChangePassword(record)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Change password successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Old password is incorrect."));
        }
    }

    @GetMapping()
    public ResponseEntity<?> getSelf() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Get info successfully.", profileService.getSelfInformation()));
    }

    @PatchMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateSelf(@ModelAttribute ChangeInfoRecord record) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Update profile successfully.", profileService.updateProfile(record)));
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteSelf() {
        if (accountService.delete(accountService.getAuthenticatedAccount().getId())) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Delete successfully."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Delete failed."));
    }

    @PostMapping(value = "/verification", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> verifySelf(@ModelAttribute VerificationRecord record) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Verified success, wait for admin accept.", profileService.verifySelf(record)));
    }
}
