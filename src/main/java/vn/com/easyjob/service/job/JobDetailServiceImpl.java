package vn.com.easyjob.service.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.easyjob.base.BaseService;
import vn.com.easyjob.base.CustomPageResponse;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.exception.ErrorHandler;
import vn.com.easyjob.model.dto.ImageDTO;
import vn.com.easyjob.model.dto.JobDTO;
import vn.com.easyjob.model.dto.JobSkillDTO;
import vn.com.easyjob.model.dto.JobTypeDTO;
import vn.com.easyjob.model.entity.*;
import vn.com.easyjob.model.record.JobDetailRecord;
import vn.com.easyjob.repository.JobApprovalStatusRepository;
import vn.com.easyjob.repository.JobDetailRepository;
import vn.com.easyjob.repository.JobSkillRepository;
import vn.com.easyjob.repository.JobTypeRepository;
import vn.com.easyjob.service.auth.ProfileService;
import vn.com.easyjob.service.image.ImageJobDetailService;
import vn.com.easyjob.specification.JobDetailSpecification;
import vn.com.easyjob.util.DateTimeFormat;
import vn.com.easyjob.util.JobApprovalStatusEnum;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobDetailServiceImpl extends BaseService<JobDetail, Long> implements JobDetailService {
    private static final Logger log = LoggerFactory.getLogger(JobDetailServiceImpl.class);
    @Autowired
    private JobDetailRepository jobDetailRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private JobTypeRepository jobTypeRepository;
    @Autowired
    private ImageJobDetailService imageJobDetailService;
    @Autowired
    private JobDetailSpecification jobDetailSpecification;
    @Autowired
    private DateTimeFormat dateTimeFormat;
    @Autowired
    private JobTypeService jobTypeService;
    @Autowired
    private JobApprovalStatusRepository approvalStatusRepository;
    @Autowired
    private JobApprovalStatusRepository jobApprovalStatusRepository;
    @Autowired
    private JobSkillRepository jobSkillRepository;

    public static JobDTO toDTO(JobDetail jobDetail) {
        return JobDTO.builder()
                .jobId(jobDetail.getId())  // Gán ID công việc
                .title(jobDetail.getTitle())  // Gán tiêu đề công việc
                .description(jobDetail.getDescription())  // Gán mô tả công việc
                .address(jobDetail.getAddress())  // Gán địa chỉ công việc
                .phone(jobDetail.getPhone())  // Gán số điện thoại liên hệ
                .contactPerson(jobDetail.getPoster() != null ? jobDetail.getPoster().getFullname() : null)  // Gán người liên hệ
                .startDate(jobDetail.getStartDate())  // Gán ngày bắt đầu công việc
                .endDate(jobDetail.getEndDate())
                .duration(jobDetail.getDuration())  // Gán thời gian làm việc
                .jobType(JobTypeDTO.builder()  // Chuyển đổi loại công việc
                        .id(jobDetail.getJobType() != null ? jobDetail.getJobType().getId() : null)
                        .name(jobDetail.getJobType() != null ? jobDetail.getJobType().getName() : null)
                        .description(jobDetail.getJobType() != null ? jobDetail.getJobType().getDescription() : null)
                        .minPrice(jobDetail.getJobType() != null ? jobDetail.getJobType().getMinPrice() : null)
                        .maxPrice(jobDetail.getJobType() != null ? jobDetail.getJobType().getMaxPrice() : null)
                        .build()
                )
                .approvalStatus(jobDetail.getJobApprovalStatus().getName().getName())
                .images(Optional.ofNullable(jobDetail.getImageJobDetails())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(image -> ImageDTO.builder()
                                .url(image.getUrl())
                                .cloudiaryPuclicUrl(image.getCloudiaryPuclicUrl())
                                .typeOfImg(image.getTypeOfImg())
                                .build())
                        .collect(Collectors.toList())
                )  // Gán danh sách hình ảnh
                .jobSkills(Optional.ofNullable(jobDetail.getJobSkills())
                        .orElse(Collections.emptySet()) // Trả về danh sách rỗng nếu jobSkills là null
                        .stream()
                        .map(jobSkill -> JobSkillDTO.builder()
                                .skill(jobSkill.getSkill())
                                .description(jobSkill.getDescription())
                                .id(jobSkill.getId())
                                .build())
                        .collect(Collectors.toSet()))  // Gán danh sách kỹ năng thích hợp cho công việc
                .postedDate(jobDetail.getCreatedDate())  // Gán ngày đăng công việc
                .verified(jobDetail.getIsDeleted())  // Gán trạng thái xác thực công việc (có thể là isDeleted hay isVerified, tùy vào logic của bạn)
                .jobApprovalStatus(jobDetail.getJobApprovalStatus().getName())
                .build();
    }

    @Override
    protected IRepository<JobDetail, Long> getRepository() {
        return jobDetailRepository;
    }

    @Override
    public CustomPageResponse<JobDTO> findAllJobs(Map<String, String> params) {
        // Xử lý tham số phân trang và sắp xếp
        // Kiểm tra "page" có tồn tại và không phải null
        String pageParam = params.getOrDefault("page", "0");
        int page = (pageParam != null && pageParam.matches("\\d+")) ? Integer.parseInt(pageParam) : 0;
        // Kiểm tra "size" có tồn tại và không phải null
        String sizeParam = params.getOrDefault("size", "12");
        int size = (sizeParam != null && sizeParam.matches("\\d+")) ? Integer.parseInt(sizeParam) : 12;
        String sortBy = params.getOrDefault("sortBy", "id");
        String direction = params.getOrDefault("direction", "asc");

        // Tạo đối tượng Pageable để phân trang và sắp xếp
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));

        // Xử lý các tham số tìm kiếm từ params
        String title = params.get("title");
        Integer districtId = params.containsKey("districtId") && params.get("districtId") != null ? Integer.parseInt(params.get("districtId")) : null;
        Integer provinceId = params.containsKey("provinceId") && params.get("provinceId") != null ? Integer.parseInt(params.get("provinceId")) : null;
        LocalDateTime startDate = params.containsKey("startDate") && params.get("startDate") != null ? dateTimeFormat.parseStringToLocalDate(params.get("startDate")).atStartOfDay() : null;
        LocalDateTime endDate = params.containsKey("endDate") && params.get("endDate") != null ? dateTimeFormat.parseStringToLocalDate(params.get("endDate")).atStartOfDay() : null;
        String jobTypeId = params.get("jobTypeId");
        JobType jobType = jobTypeId != null ? jobTypeService.findOne(Long.parseLong(jobTypeId)) : null;
        JobApprovalStatusEnum approvalStatus = params.containsKey("approval") && params.get("approval") != null ? JobApprovalStatusEnum.valueOf(params.get("approval")) : JobApprovalStatusEnum.APPROVED;
        JobApprovalStatus jobApprovalStatus = jobApprovalStatusRepository.findByName(approvalStatus).orElseThrow(() -> new RuntimeException("Approval status not found"));
        Integer jobSkillId = params.containsKey("jobSkillId") && params.get("jobSkillId") != null ? Integer.parseInt(params.get("jobSkillId")) : null;
        JobSkill jobSkill = Optional.ofNullable(jobSkillId)
                .map(id -> jobSkillRepository.findById(id.longValue())
                        .orElseThrow(() -> new RuntimeException("Job skill not found")))
                .orElse(null);  // Trả về null nếu jobSkillId là null
        // Kết hợp các Specification, chỉ tạo các điều kiện khi giá trị không null
        Specification<JobDetail> spec = Specification.where(
                        (title != null ? jobDetailSpecification.hasTitle(title) : null))
                .and(districtId != null ? jobDetailSpecification.hasDistrictId(districtId) : null)
                .and(provinceId != null ? jobDetailSpecification.hasProvinceId(provinceId) : null)
                .and((startDate != null || endDate != null) ? jobDetailSpecification.isWithinDateRange(startDate, endDate) : null)
                .and(jobDetailSpecification.hasApprovalStatus(jobApprovalStatus))
                .and(jobSkill != null ? jobDetailSpecification.hasJobSkill(jobSkill) : null)
                .and(jobType != null ? jobDetailSpecification.hasJobType(jobType) : null);

        // Lấy danh sách phân trang của JobDetail từ repository
        CustomPageResponse<JobDetail> jobDetailsPage = jobDetailRepository.findCustomPage(pageable, spec, JobDetail.class);

        // Chuyển đổi từ JobDetail sang JobDTO với phương thức map
        CustomPageResponse<JobDTO> jobDTOPage = jobDetailsPage.map(JobDetailServiceImpl::toDTO);

        return jobDTOPage;
    }


    @Override
    @Transactional
    public void createJob(JobDetailRecord request) throws Exception {
        // Lấy đối tượng Authentication từ SecurityContextHolder
        Profile profile = profileService.getAuthenticatedProfile();

        // Lấy thông tin JobType
        JobType jobType = jobTypeRepository.findById(request.jobTypeId())
                .orElseThrow(() -> new ErrorHandler(HttpStatus.NOT_FOUND, "JobType Not found"));

        // Tạo đối tượng JobDetail và lưu vào cơ sở dữ liệu
        JobDetail jobDetail = saveJobDetail(request, profile, jobType);

        // Lưu ảnh nếu có
        if (request.imageJobDetails() != null) {
            imageJobDetailService.saveImageOfJobDetail(request.imageJobDetails(), jobDetail.getId(), "");
        }
    }

    @Override
    public JobDTO findJobById(Long id) {
        return toDTO(jobDetailRepository.findById(id).orElseThrow(() -> new ErrorHandler(HttpStatus.NOT_FOUND, "Job Not found")));
    }

    @Override
    public void toggleAcceptJob(Long id) {
        JobDetail jobDetail = jobDetailRepository.findById(id).orElseThrow(() -> new ErrorHandler(HttpStatus.NOT_FOUND, "Job Not found"));
        jobDetail.setJobApprovalStatus( jobApprovalStatusRepository.findByName(JobApprovalStatusEnum.APPROVED).orElseThrow(
                () -> new ErrorHandler(HttpStatus.NOT_FOUND, "ApprovalStatus Not found")
        ));
        jobDetailRepository.save(jobDetail);
    }

    @Override
    public void toggleRejectJob(Long id) {
        JobDetail jobDetail = jobDetailRepository.findById(id).orElseThrow(() -> new ErrorHandler(HttpStatus.NOT_FOUND, "Job Not found"));
        jobDetail.setJobApprovalStatus( jobApprovalStatusRepository.findByName(JobApprovalStatusEnum.REJECTED).orElseThrow(
                () -> new ErrorHandler(HttpStatus.NOT_FOUND, "ApprovalStatus Not found")
        ));
        jobDetailRepository.save(jobDetail);
    }

    // Phương thức lưu JobDetail
    private JobDetail saveJobDetail(JobDetailRecord request, Profile profile, JobType jobType) {
        return jobDetailRepository.save(
                JobDetail.builder()
                        .jobType(jobType)
                        .address(request.address())
                        .provinceId(request.provinceId())
                        .districtId(request.districtId())
                        .title(request.title())
                        .description(request.description())
                        .startDate(request.startDate())
                        .endDate(request.endDate())
                        .phone(request.phone())
                        .duration(request.duration())
                        .poster(profile)
                        .jobApprovalStatus(
                                jobApprovalStatusRepository.findByName(JobApprovalStatusEnum.PENDING).orElseThrow(
                                     () -> new ErrorHandler(HttpStatus.NOT_FOUND, "ApprovalStatus Not found")
                                )
                        )
                        .build()
        );
    }
}
