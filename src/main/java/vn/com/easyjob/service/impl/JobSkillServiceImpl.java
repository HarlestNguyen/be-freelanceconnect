package vn.com.easyjob.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseAbstractService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.JobSkill;
import vn.com.easyjob.repository.JobSkillRepository;
import vn.com.easyjob.service.JobSkillService;

@Service
public class JobSkillServiceImpl extends BaseAbstractService<JobSkill, Long> implements JobSkillService {
    @Autowired
    private JobSkillRepository jobSkillRepository;

    @Override
    protected IRepository<JobSkill, Long> getRepository() {
        return jobSkillRepository;
    }
}
