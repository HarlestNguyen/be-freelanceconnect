package vn.com.easyjob.service.applie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseService;
import vn.com.easyjob.base.CustomPageResponse;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.exception.ErrorHandler;
import vn.com.easyjob.model.dto.AppliesDTO;
import vn.com.easyjob.model.dto.JobDTO;
import vn.com.easyjob.model.dto.ProfileAppliesDTO;
import vn.com.easyjob.model.entity.AppliedJob;
import vn.com.easyjob.model.entity.JobDetail;
import vn.com.easyjob.model.entity.Profile;
import vn.com.easyjob.repository.ApplieStatusRepository;
import vn.com.easyjob.repository.AppliedJobRepository;
import vn.com.easyjob.repository.JobDetailRepository;
import vn.com.easyjob.service.auth.ProfileService;
import vn.com.easyjob.service.auth.ProfileServiceImpl;
import vn.com.easyjob.service.job.JobDetailService;
import vn.com.easyjob.service.job.JobDetailServiceImpl;
import vn.com.easyjob.util.ApplieStatusEnum;
import vn.com.easyjob.util.JobApprovalStatusEnum;
import vn.com.easyjob.util.RoleEnum;

@Service
public class AppliedJobServiceImpl extends BaseService<AppliedJob, Long> implements AppliedJobService {

    @Autowired
    private AppliedJobRepository appliedJobRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private JobDetailRepository jobDetailRepository;

    @Autowired
    private ApplieStatusRepository applieStatusRepository;

    @Override
    protected IRepository<AppliedJob, Long> getRepository() {
        return appliedJobRepository;
    }



    @Override
    public AppliesDTO addAppliedJob(Long jobId) {
        Profile profile = profileService.getAuthenticatedProfile();
        if (!profile.getIsVerified())throw new ErrorHandler(HttpStatus.NOT_FOUND,"account not verified");
        JobDetail jobDetail = jobDetailRepository.findById(jobId).orElseThrow(
                () -> new ErrorHandler(HttpStatus.NOT_FOUND,"JobDetail Not found")
        );
        if (!jobDetail.getJobApprovalStatus().getName().equals(JobApprovalStatusEnum.APPROVED))
            throw new ErrorHandler(HttpStatus.NOT_FOUND,"JobDetail Not is not APPROVED");

        AppliedJob appliedJobTmp =  AppliedJob.builder()
                .applier(profile)
                .jobDetail(jobDetail)
                .applieStatus(applieStatusRepository.findByName(ApplieStatusEnum.WAITING).orElseThrow(
                        () -> new ErrorHandler(HttpStatus.NOT_FOUND,"ApplieStatus Not found")
                ))
                .build();
        appliedJobTmp.setIsDeleted(false);
       AppliedJob appliedJob = appliedJobRepository.save(
               appliedJobTmp
       );

        return AppliesDTO.builder()
                .job(JobDetailServiceImpl.toDTO(appliedJob.getJobDetail()))
                .applieStatus(appliedJob.getApplieStatus().getName())
                .applier(appliedJob.getApplier().getAccount().getEmail())
                .build();
    }

    @Override
    public CustomPageResponse<ProfileAppliesDTO> getAppliedJobs(Long jobId, ApplieStatusEnum applieStatus, Pageable pageable) {
        Profile profileAuth = profileService.getAuthenticatedProfile();
//        if(!jobDetailRepository.existsByPosterId(profileAuth.getId())) throw new ErrorHandler(HttpStatus.NOT_FOUND,"account not access denied");
        Page<Profile> profile = appliedJobRepository.findByJobDetailId(applieStatus,jobId,pageable);
        return CustomPageResponse.<ProfileAppliesDTO>builder()
                .totalElements(profile.getTotalElements())
                .totalPages(profile.getTotalPages())
                .first(profile.isFirst())
                .last(profile.isLast())
                .numberOfElements(profile.getNumberOfElements())
                .size(profile.getSize())
                .empty(profile.isEmpty())
                .number(profile.getNumber())
                .content(
                        profile.getContent().stream().map(
                        ProfileServiceImpl::convertToAppliesDTO
                        ).toList()
                )
                .build();
    }

}
