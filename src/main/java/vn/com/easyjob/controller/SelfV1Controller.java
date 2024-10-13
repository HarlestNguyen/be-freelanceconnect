package vn.com.easyjob.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.easyjob.model.dto.JobSkillDTO;
import vn.com.easyjob.model.dto.ProfileDTO;
import vn.com.easyjob.model.dto.ResponseDTO;
import vn.com.easyjob.model.entity.Profile;
import vn.com.easyjob.model.record.ChangeInfoRecord;
import vn.com.easyjob.model.record.ChangePasswordRecord;
import vn.com.easyjob.service.auth.AccountService;
import vn.com.easyjob.service.auth.ProfileService;
import vn.com.easyjob.service.cloudiary.CloudinaryService;
import vn.com.easyjob.service.job.JobSkillService;
import vn.com.easyjob.util.ApplieStatusEnum;
import vn.com.easyjob.util.AuthConstants;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Period;
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
        Profile profile = profileService.getAuthenticatedProfile();
        ProfileDTO dto = ProfileDTO.builder()
                .age(Period.between(profile.getDob().toLocalDate(), LocalDate.now()).getYears())
                .address(profile.getAddress())
                .avatar(profile.getAvatar())
                .createdDate(profile.getCreatedDate())
                .provinceId(profile.getProvinceId())
                .districtId(profile.getDistrictId())
                .fullname(profile.getFullname())
                .isVerified(profile.getCitizenIdentityCard().getIsVerified())
                .jobSkills(profile.getJobSkills().stream().filter(jobSkill -> !jobSkill.getIsDeleted()).map(jobSkill -> JobSkillDTO.builder()
                        .skill(jobSkill.getSkill())
                        .id(jobSkill.getId())
                        .description(jobSkill.getDescription())
                        .build()
                ).toList())
//                .star()
                .numOfJob((int) profile.getAppliedJobs().stream().filter(appliedJob -> appliedJob.getApplieStatus().getName().equals(ApplieStatusEnum.COMPLETED.name())).count())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Get info successfully.", dto));
    }

    @PatchMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateSelf(@ModelAttribute ChangeInfoRecord record) throws Exception {
        Profile profile = profileService.getAuthenticatedProfile();
        Field[] fields = record.getClass().getDeclaredFields();
        for (Field field : fields) {
            switch (field.getName()) {
                case "fullname":
                    profile.setFullname(record.fullname());
                    break;
                case "dob":
                    break;
                case "address":
                    profile.setAddress(record.address());
                    break;
                case "avatar":
                    Map<String, Object> uploadResult = cloudinaryService.uploadImage(record.avatar(), UUID.randomUUID().toString());
                    profile.setAvatar(uploadResult.get("secure_url").toString());
                    break;
                case "districtId":
                    profile.setDistrictId(record.districtId());
                    break;
                case "provinceId":
                    profile.setProvinceId(record.provinceId());
                    break;
                case "skillIds":
                    profile.setJobSkills(Arrays.stream(record.skillIds()).map(id -> jobSkillService.findOne(id.longValue())).toList());
                    break;
                default:
                    break;
            }
        }
//        Account account = accountService.getAuthenticatedAccount();
//        Profile profile = account.getProfile();
//        profile.setFullname(record.fullname());
//        profile.setPhone(record.phone());
//        account.setProfile(profile);
//        account = accountService.save(account);
//        ProfileDTO p = ProfileDTO.builder()
//                .id(account.getId())
//                .email(account.getProfile().getEmail())
//                .roleName(account.getRole().getName())
//                .fullname(account.getProfile().getFullname())
//                .isVerified(account.isVerified())
//                .build();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Update profile successfully.", profileService.save(profile)));
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteSelf() {
        if (accountService.delete(accountService.getAuthenticatedAccount().getId())) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Delete successfully."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Delete failed."));
    }
}
