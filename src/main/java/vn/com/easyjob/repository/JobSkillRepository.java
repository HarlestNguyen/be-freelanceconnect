package vn.com.easyjob.repository;

import org.springframework.stereotype.Repository;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.JobSkill;

@Repository
public interface JobSkillRepository extends IRepository<JobSkill, Long> {
}
