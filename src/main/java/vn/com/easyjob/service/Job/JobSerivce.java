package vn.com.easyjob.service.Job;

import org.springframework.data.domain.Pageable;
import vn.com.easyjob.base.CustomPageResponse;
import vn.com.easyjob.model.dto.JobDTO;
import vn.com.easyjob.model.record.JobDetailRecord;


public interface JobSerivce {
    CustomPageResponse<JobDTO> findAllJobs(Pageable pageable);

    void createJob(JobDetailRecord request) throws Exception;

    JobDTO findJobById(Long id);
}
