package vn.com.easyjob.service.job;

import org.springframework.data.domain.Pageable;
import vn.com.easyjob.base.CustomPageResponse;
import vn.com.easyjob.base.IService;
import vn.com.easyjob.model.dto.JobDTO;
import vn.com.easyjob.model.entity.JobDetail;
import vn.com.easyjob.model.record.JobDetailRecord;
import vn.com.easyjob.util.ApplieStatusEnum;
import vn.com.easyjob.util.JobApprovalStatusEnum;

import java.util.Map;

public interface JobDetailService extends IService<JobDetail, Long> {
    CustomPageResponse<JobDTO> findAllJobs(Map<String, String> params);

    void createJob(JobDetailRecord request) throws Exception;

    JobDTO findJobById(Long id);

    void toggleAcceptJob(Long id);

    void toggleRejectJob(Long id);

    CustomPageResponse<JobDTO> findJobApprovalBySelf(Pageable pageable, JobApprovalStatusEnum jobApprovalStatusEnum);
    CustomPageResponse<JobDTO> findJobApplieStatusEnumBySelf(Pageable pageable, ApplieStatusEnum applieStatusEnum);

}
