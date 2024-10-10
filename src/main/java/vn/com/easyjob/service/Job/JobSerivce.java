package vn.com.easyjob.service.Job;

import org.springframework.data.domain.Pageable;
import vn.com.easyjob.base.CustomPageResponse;
import vn.com.easyjob.model.dto.JobDTO;
import vn.com.easyjob.model.record.JobDetailRequest;


public interface JobSerivce {
    CustomPageResponse<JobDTO> findAllJobs(Pageable pageable);

    void createJob(JobDetailRequest request) throws Exception;
}
