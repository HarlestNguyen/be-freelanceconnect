package vn.com.easyjob.service.job;

import vn.com.easyjob.base.IService;
import vn.com.easyjob.model.dto.JobSkillDTO;
import vn.com.easyjob.model.entity.JobSkill;
import vn.com.easyjob.model.record.JobSkillRecord;

public interface JobSkillService extends IService<JobSkill, Long> {
    JobSkillDTO save(JobSkillRecord record);

    JobSkillDTO update(Long id, JobSkillRecord record);
}
