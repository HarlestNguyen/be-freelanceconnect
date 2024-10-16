package vn.com.easyjob.service.job;

import vn.com.easyjob.base.IService;
import vn.com.easyjob.model.dto.JobTypeDTO;
import vn.com.easyjob.model.entity.JobType;
import vn.com.easyjob.model.record.JobTypeRecord;

public interface JobTypeService extends IService<JobType, Long> {
    JobTypeDTO save(JobTypeRecord record);

    JobTypeDTO update(Long id, JobTypeRecord record);
}
