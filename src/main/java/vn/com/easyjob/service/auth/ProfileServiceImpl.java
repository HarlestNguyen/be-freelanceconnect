package vn.com.easyjob.service.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.com.easyjob.base.BaseService;
import vn.com.easyjob.base.CustomPageResponse;
import vn.com.easyjob.base.IRepository;

import vn.com.easyjob.exception.ErrorHandler;
import vn.com.easyjob.model.dto.CitizenIdentityCardDTO;
import vn.com.easyjob.model.dto.JobSkillDTO;
import vn.com.easyjob.model.dto.ProfileAppliesDTO;
import vn.com.easyjob.model.dto.ProfileDTO;
import vn.com.easyjob.model.entity.CitizenIdentityCard;
import vn.com.easyjob.model.entity.JobSkill;
import vn.com.easyjob.model.entity.Profile;
import vn.com.easyjob.model.mapper.CitizenIdentityCardMapper;
import vn.com.easyjob.model.mapper.CitizenIdentityCardMapperImpl;
import vn.com.easyjob.model.record.ChangeInfoRecord;
import vn.com.easyjob.model.record.VerificationRecord;
import vn.com.easyjob.repository.CitizenIdentityCardRepository;
import vn.com.easyjob.repository.ProfileRepository;
import vn.com.easyjob.response.ResponseListPagination;
import vn.com.easyjob.service.cloudiary.CloudinaryService;
import vn.com.easyjob.service.job.JobSkillService;
import vn.com.easyjob.specification.ProfileSpecification;
import vn.com.easyjob.util.ApplieStatusEnum;
import vn.com.easyjob.util.DateTimeFormat;
import org.springframework.data.domain.Pageable;
import java.lang.reflect.Field;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private CitizenIdentityCardRepository citizenIdentityCardRepository;

    @Autowired
    private CitizenIdentityCardMapper citizenIdentityCardMapper;

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
        dto.setId(profile.getId());
        dto.setPhone(profile.getPhone());
        dto.setDob(profile.getDob());
        if (profile.getCitizenIdentityCard() != null){
            dto.setImgFrontOfCard(profile.getCitizenIdentityCard().getFrontOfCard());
            dto.setImgBackOfCard(profile.getCitizenIdentityCard().getBackOfCard());
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
        dto.setNumOfJob((int) profile.getAppliedJobs().stream().filter(appliedJob -> appliedJob.getApplieStatus().getName().equals(ApplieStatusEnum.COMPLETED)).count());
        dto.setEmail(profile.getAccount().getEmail());
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
                case "phone":
                    if (record.phone()!=null){
                        profile.setPhone(record.phone());
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
        profileDTO.setId(rs.getId());
        profileDTO.setEmail(rs.getAccount().getEmail());
        profileDTO.setPhone(rs.getPhone());
        profileDTO.setDob(rs.getDob());
        profileDTO.setNumOfJob((int) profile.getAppliedJobs().stream().filter(appliedJob -> appliedJob.getApplieStatus().getName().equals(ApplieStatusEnum.COMPLETED.name())).count());
        return profileDTO;


    }

    @Override
    public CustomPageResponse<ProfileDTO> getProfiles(String keyword, Boolean isDeleted, Integer role, Pageable pageable) {
        // Khởi tạo Specification là null, sau đó thêm điều kiện khi trường có giá trị
        Specification<Profile> spec = Specification.where(ProfileSpecification.hasRole(role));

        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and(ProfileSpecification.hasKeyword(keyword));
        }
        if (isDeleted != null) {
            spec = spec.and(ProfileSpecification.hasIsDeleted(isDeleted));
        }
//        if (role != null) {
//
//        }
        // Gọi findAll với Specification và Pageable
        Page<Profile> profiles = profileRepository.findAll(spec, pageable);
        return CustomPageResponse.<ProfileDTO>builder()
                .content(profiles.stream().map(ProfileServiceImpl::convertToDTO).toList())
                .last(profiles.isLast())
                .totalPages(profiles.getTotalPages())
                .empty(profiles.isEmpty())
                .size(profiles.getSize())
                .number(profiles.getNumber())
                .first(profiles.isFirst())
                .numberOfElements(profiles.getNumberOfElements())
                .totalElements(profiles.getTotalElements())
                .build();
    }


    @Override
    public void ChangeIsdel(int id) {
      Profile p =  profileRepository.findById(id).orElseThrow(
                () -> new RuntimeException("profile not found")
        );
      p.setIsDeleted(!p.getIsDeleted());
      profileRepository.save(p);
    }

    @Override
    public CitizenIdentityCardDTO verifySelf(VerificationRecord record) {
        try {
            Profile profile = getAuthenticatedProfile();
            CitizenIdentityCard cic = new CitizenIdentityCard();
            cic.setProfile(profile);
            cic.setNo(record.no());
            cic.setFullname(record.fullname());
            cic.setDob(dateTimeFormat.parseStringToLocalDate(record.dateOfBirth()));
            cic.setGender(record.gender());
            cic.setPlaceOfPrecidence(record.placeOfPrecidence());
            cic.setDateOfIssue(dateTimeFormat.parseStringToLocalDate(record.dateOfIssue()));
            if (record.backOfCard() != null && record.frontOfCard() != null){
                ArrayList<MultipartFile> files = new ArrayList<>();
                files.add(record.frontOfCard());
                files.add(record.backOfCard());
                List<String> rs = cloudinaryService.uploadImages(files);
                if (rs.size() == 2){
                    cic.setFrontOfCard(rs.get(0));
                    cic.setBackOfCard(rs.get(1));
                }else throw new RuntimeException("Upload image failed");
            }else throw new RuntimeException("Front and back image can not be null");
            return citizenIdentityCardMapper.toDto(citizenIdentityCardRepository.save(cic));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ProfileDTO convertToDTO(Profile profile) {
        ProfileDTO profileDTO = ProfileDTO.builder()
                .id(profile.getId())
                .phone(profile.getPhone())
                .email(profile.getAccount().getEmail())
                .fullname(profile.getFullname())
                .address(profile.getAddress())
                .avatar(profile.getAvatar())
                .districtId(profile.getDistrictId())
                .provinceId(profile.getProvinceId())
                .isVerified(profile.getIsVerified())
                .dob(profile.getDob())
                .createdDate(profile.getCreatedDate()) // Assuming this field exists in Profile
                .build();
        // Tính toán tuổi nếu profile có ngày sinh
        if (profile.getDob() != null) {
            profileDTO.setAge(Period.between(profile.getDob(), LocalDate.now()).getYears());
        }
        // Lọc và chuyển đổi các kỹ năng công việc (jobSkills)
        profileDTO.setJobSkills(profile.getJobSkills().stream()
                .filter(jobSkill -> !jobSkill.getIsDeleted()) // Chỉ lấy những kỹ năng không bị xóa
                .map(jobSkill -> JobSkillDTO.builder()
                        .id(jobSkill.getId())
                        .skill(jobSkill.getSkill())
                        .description(jobSkill.getDescription())
                        .build())
                .toList());
        // Tính số lượng công việc đã hoàn thành
        profileDTO.setNumOfJob((int) profile.getAppliedJobs().stream()
                .filter(appliedJob -> appliedJob.getApplieStatus().getName().equals(ApplieStatusEnum.COMPLETED.name()))
                .count());

        // Nếu có thuộc tính "star" trong DTO mà bạn muốn thiết lập sau, có thể để null hoặc tính toán nếu cần
        profileDTO.setStar(null);

        return profileDTO;
    }

    public static ProfileAppliesDTO convertToAppliesDTO(Profile profile) {
        ProfileAppliesDTO profileAppliesDTO = ProfileAppliesDTO.builder()
                .id(profile.getId())
                .fullname(profile.getFullname())
                .address(profile.getAddress())
                .avatar(profile.getAvatar())
                .isVerified(profile.getIsVerified())
                .createdDate(profile.getCreatedDate())
                .build();

        // Tính toán tuổi nếu profile có ngày sinh
        if (profile.getDob() != null) {
            profileAppliesDTO.setAge(Period.between(profile.getDob(), LocalDate.now()).getYears());
        }

        // Lọc và chuyển đổi các kỹ năng công việc (jobSkills)
        profileAppliesDTO.setJobSkills(profile.getJobSkills().stream()
                .filter(jobSkill -> !jobSkill.getIsDeleted()) // Chỉ lấy những kỹ năng không bị xóa
                .map(jobSkill -> JobSkillDTO.builder()
                        .id(jobSkill.getId())
                        .skill(jobSkill.getSkill())
                        .description(jobSkill.getDescription())
                        .build())
                .toList());

        int numOfJob = (int)profile.getAppliedJobs().stream()
                .filter(appliedJob -> appliedJob.getApplieStatus().getName().equals(ApplieStatusEnum.COMPLETED))
                .count();
        // Thuộc tính numOfJob để null cho bạn tự viết logic
        profileAppliesDTO.setNumOfJob(numOfJob); // Placeholder cho numOfJob
        int hourOfWork = (int) (int)profile.getAppliedJobs().stream()
                .filter(appliedJob -> appliedJob.getApplieStatus().getName().equals(ApplieStatusEnum.COMPLETED))
                .mapToInt(t -> t.getJobDetail().getDuration()).sum();
        profileAppliesDTO.setHourOfWork(hourOfWork);
        // Nếu có thuộc tính star trong ProfileAppliesDTO, đặt null hoặc tự tính toán nếu cần
        profileAppliesDTO.setStar(null);

        return profileAppliesDTO;
    }
}
