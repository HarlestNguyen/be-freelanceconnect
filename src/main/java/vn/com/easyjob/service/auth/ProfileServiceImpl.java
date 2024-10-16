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
import vn.com.easyjob.model.entity.Profile;
import vn.com.easyjob.repository.ProfileRepository;
import vn.com.easyjob.util.ApplieStatusEnum;

import java.time.LocalDate;
import java.time.Period;

@Service
public class ProfileServiceImpl extends BaseService<Profile, Integer> implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

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
            dto.setAge(Period.between(profile.getDob().toLocalDate(), LocalDate.now()).getYears());
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


}
