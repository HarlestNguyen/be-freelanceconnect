package vn.com.easyjob.service.job;

import vn.com.easyjob.base.IService;
import vn.com.easyjob.model.dto.JobSkillDTO;
import vn.com.easyjob.model.dto.JobTypeDTO;
import vn.com.easyjob.model.entity.JobSkill;
import vn.com.easyjob.model.record.JobSkillRecord;
import vn.com.easyjob.model.record.JobtypeRecord;

public interface JobSkillService extends IService<JobSkill, Long> {

    JobSkillDTO updateJobSkill(JobSkillDTO jobSkill);

    JobSkillDTO save(JobSkillRecord jobSkill);
}
