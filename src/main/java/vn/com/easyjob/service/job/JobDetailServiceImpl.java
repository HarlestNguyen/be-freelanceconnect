package vn.com.easyjob.service.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.easyjob.base.BaseAbstractService;
import vn.com.easyjob.base.CustomPageResponse;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.exception.ErrorHandler;
import vn.com.easyjob.model.dto.ImageDTO;
import vn.com.easyjob.model.dto.JobDTO;
import vn.com.easyjob.model.dto.JobTypeDTO;
import vn.com.easyjob.model.entity.JobDetail;
import vn.com.easyjob.model.entity.JobType;
import vn.com.easyjob.model.entity.Profile;
import vn.com.easyjob.model.record.JobDetailRecord;
import vn.com.easyjob.repository.JobDetailRepository;
import vn.com.easyjob.repository.JobTypeRepository;
import vn.com.easyjob.service.auth.ProfileService;
import vn.com.easyjob.service.image.ImageJobDetailService;

import java.util.stream.Collectors;

@Service
public class JobDetailServiceImpl extends BaseAbstractService<JobDetail, Long> implements JobDetailService {
    @Autowired
    private JobDetailRepository jobDetailRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private JobTypeRepository jobTypeRepository;
    @Autowired
    private ImageJobDetailService imageJobDetailService;

    public static JobDTO fromJobDetail(JobDetail jobDetail) {
        return JobDTO.builder()
                .jobId(jobDetail.getId())  // Gán ID công việc
                .title(jobDetail.getTitle())  // Gán tiêu đề công việc
                .description(jobDetail.getDesciption())  // Gán mô tả công việc
                .address(jobDetail.getAddress())  // Gán địa chỉ công việc
                .phone(jobDetail.getPhone())  // Gán số điện thoại liên hệ
                .contactPerson(jobDetail.getPoster() != null ? jobDetail.getPoster().getFullname() : null)  // Gán người liên hệ
                .startDate(jobDetail.getStartDate())  // Gán ngày bắt đầu công việc
                .duration(jobDetail.getDuration())  // Gán thời gian làm việc
                .jobType(JobTypeDTO.builder()  // Chuyển đổi loại công việc
                        .id(jobDetail.getJobType() != null ? jobDetail.getJobType().getId() : null)
                        .name(jobDetail.getJobType() != null ? jobDetail.getJobType().getName() : null)
                        .description(jobDetail.getJobType() != null ? jobDetail.getJobType().getDescription() : null)
                        .minPrice(jobDetail.getJobType() != null ? jobDetail.getJobType().getMinPrice() : null)
                        .maxPrice(jobDetail.getJobType() != null ? jobDetail.getJobType().getMaxPrice() : null)
                        .build()
                )
                .images(jobDetail.getImageJobDetails().stream()
                        .map(image -> ImageDTO.builder()
                                .url(image.getUrl())
                                .cloudiaryPuclicUrl(image.getCloudiaryPuclicUrl())
                                .typeOfImg(image.getTypeOfImg())
                                .build())
                        .collect(Collectors.toList())
                )  // Gán danh sách hình ảnh
                .postedDate(jobDetail.getCreatedDate())  // Gán ngày đăng công việc
                .verified(jobDetail.getIsDeleted())  // Gán trạng thái xác thực công việc (có thể là isDeleted hay isVerified, tùy vào logic của bạn)
                .build();
    }

    @Override
    protected IRepository<JobDetail, Long> getRepository() {
        return jobDetailRepository;
    }

    @Override
    public CustomPageResponse<JobDTO> findAllJobs(Pageable pageable) {
        // Lấy danh sách phân trang của JobDetail từ repository
        CustomPageResponse<JobDetail> jobDetailsPage = jobDetailRepository.findCustomPage(pageable, JobDetail.class);
        // Chuyển đổi từ JobDetail sang JobDTO với phương thức builder
        CustomPageResponse<JobDTO> jobDTOPage = jobDetailsPage.map(JobServiceImpl::fromJobDetail);

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
        return fromJobDetail(jobDetailRepository.findById(id).orElseThrow(() -> new ErrorHandler(HttpStatus.NOT_FOUND, "Job Not found")));
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
                        .desciption(request.description())
                        .startDate(request.startDate())
                        .endDate(request.endDate())
                        .phone(request.phone())
                        .duration(request.duration())
                        .poster(profile)
                        .build()
        );
    }
}
