package vn.com.easyjob.service.job;

import org.springframework.data.domain.Pageable;
import vn.com.easyjob.base.CustomPageResponse;
import vn.com.easyjob.base.IService;
import vn.com.easyjob.model.dto.JobDTO;
import vn.com.easyjob.model.entity.JobDetail;
import vn.com.easyjob.model.record.JobDetailRecord;

public interface JobDetailService extends IService<JobDetail, Long> {
    CustomPageResponse<JobDTO> findAllJobs(Pageable pageable);

    void createJob(JobDetailRecord request) throws Exception;

    JobDTO findJobById(Long id);
}
