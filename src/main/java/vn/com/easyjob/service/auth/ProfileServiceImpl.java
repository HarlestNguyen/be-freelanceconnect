package vn.com.easyjob.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.exception.ErrorHandler;
import vn.com.easyjob.model.dto.JobSkillDTO;
import vn.com.easyjob.model.dto.ProfileDTO;
import vn.com.easyjob.model.entity.JobSkill;
import vn.com.easyjob.model.entity.Profile;
import vn.com.easyjob.model.record.ChangeInfoRecord;
import vn.com.easyjob.repository.ProfileRepository;
import vn.com.easyjob.service.cloudiary.CloudinaryService;
import vn.com.easyjob.service.job.JobSkillService;
import vn.com.easyjob.util.ApplieStatusEnum;
import vn.com.easyjob.util.DateTimeFormat;

import java.lang.reflect.Field;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Service
public class ProfileServiceImpl extends BaseService<Profile, Integer> implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private DateTimeFormat dateTimeFormat;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private JobSkillService jobSkillService;

    @Override
    protected IRepository<Profile, Integer> getRepository() {
        return profileRepository;
    }

    // Phương thức lấy thông tin người dùng đã đăng nhập và trả về Profile
    @Override
    public Profile getAuthenticatedProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("User is not authenticated");
        }
        String email = authentication.getName();
        return profileRepository.findByAccount_Email(email).orElseThrow(() -> new ErrorHandler(HttpStatus.NOT_FOUND, "Profile Not found"));
    }

    @Override
    public ProfileDTO getSelfInformation() {
        Profile profile = getAuthenticatedProfile();
        ProfileDTO dto = new ProfileDTO();
        if (profile.getDob() != null) {
            dto.setAge(Period.between(profile.getDob(), LocalDate.now()).getYears());
        }
        dto.setFullname(profile.getFullname());
        dto.setAddress(profile.getAddress());
        dto.setAvatar(profile.getAvatar());
        dto.setCreatedDate(profile.getCreatedDate());
        dto.setProvinceId(profile.getProvinceId());
        dto.setDistrictId(profile.getDistrictId());
        dto.setIsVerified(profile.getIsVerified());
        dto.setJobSkills(profile.getJobSkills().stream().filter(jobSkill -> !jobSkill.getIsDeleted()).map(jobSkill -> JobSkillDTO.builder()
                .skill(jobSkill.getSkill())
                .id(jobSkill.getId())
                .description(jobSkill.getDescription())
                .build()).toList());
        dto.setStar(null);
        dto.setNumOfJob((int) profile.getAppliedJobs().stream().filter(appliedJob -> appliedJob.getApplieStatus().getName().equals(ApplieStatusEnum.COMPLETED.name())).count());

        return dto;
    }

    @Override
    public ProfileDTO updateProfile(ChangeInfoRecord record) {
        final Profile profile = getAuthenticatedProfile();
        Field[] fields = record.getClass().getDeclaredFields();
        for (Field field : fields) {
            switch (field.getName()) {
                case "fullname":
                    if (record.fullname()!=null){
                        profile.setFullname(record.fullname());
                    }
                    break;
                case "dob":
                    if (record.dob()!=null){
                        profile.setDob(dateTimeFormat.parseStringToLocalDate(record.dob()));
                    }
                    break;
                case "address":
                    if (record.address()!=null){
                        profile.setAddress(record.address());
                    }
                    break;
                case "avatar":
                    if (record.avatar()!=null){
                        try {
                            Map uploadResult = cloudinaryService.uploadImage(record.avatar(), UUID.randomUUID().toString());
                            profile.setAvatar(uploadResult.get("secure_url").toString());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case "districtId":
                    if (record.districtId()!=null){
                        profile.setDistrictId(record.districtId());
                    }
                    break;
                case "provinceId":
                    if (record.provinceId()!=null){
                        profile.setProvinceId(record.provinceId());
                    }
                    break;
                case "skillIds":
                    if (record.skillIds()!=null){
                        Arrays.stream(record.skillIds()).forEach(
                                skillId -> {
                                    JobSkill skill = jobSkillService.findOne(skillId);
                                    profile.getJobSkills().add(skill);
                                }
                        );
                    }
                    break;
            }
        }
        Profile rs = save(profile);

        ProfileDTO profileDTO = new ProfileDTO();
        if (profile.getDob() != null) {
            profileDTO.setAge(Period.between(profile.getDob(), LocalDate.now()).getYears());
        }
        profileDTO.setFullname(rs.getFullname());
        profileDTO.setAddress(rs.getAddress());
        profileDTO.setAvatar(rs.getAvatar());
        profileDTO.setCreatedDate(rs.getCreatedDate());
        profileDTO.setProvinceId(rs.getProvinceId());
        profileDTO.setDistrictId(rs.getDistrictId());
        profileDTO.setIsVerified(rs.getIsVerified());
        profileDTO.setJobSkills(profile.getJobSkills().stream().filter(jobSkill -> !jobSkill.getIsDeleted()).map(jobSkill -> JobSkillDTO.builder()
                .skill(jobSkill.getSkill())
                .id(jobSkill.getId())
                .description(jobSkill.getDescription())
                .build()).toList());
        profileDTO.setStar(null);
        profileDTO.setNumOfJob((int) profile.getAppliedJobs().stream().filter(appliedJob -> appliedJob.getApplieStatus().getName().equals(ApplieStatusEnum.COMPLETED.name())).count());
        return profileDTO;


    }
}
