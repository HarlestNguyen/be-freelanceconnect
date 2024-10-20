package vn.com.easyjob.service.job;

import vn.com.easyjob.base.CustomPageResponse;
import vn.com.easyjob.base.IService;
import vn.com.easyjob.model.dto.JobDTO;
import vn.com.easyjob.model.entity.JobDetail;
import vn.com.easyjob.model.record.JobDetailRecord;

import java.util.Map;

public interface JobDetailService extends IService<JobDetail, Long> {
    CustomPageResponse<JobDTO> findAllJobs(Map<String, String> params);

    void createJob(JobDetailRecord request) throws Exception;

    JobDTO findJobById(Long id);
}
