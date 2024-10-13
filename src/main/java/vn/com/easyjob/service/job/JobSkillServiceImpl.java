package vn.com.easyjob.service.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseMapper;
import vn.com.easyjob.base.BaseService;
import vn.com.easyjob.base.BaseServiceDTO;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.dto.JobSkillDTO;
import vn.com.easyjob.model.entity.JobSkill;
import vn.com.easyjob.model.mapper.JobSkillMapper;
import vn.com.easyjob.repository.JobSkillRepository;

@Service
public class JobSkillServiceImpl extends BaseServiceDTO<JobSkill, JobSkillDTO, Long> implements JobSkillService {
    @Autowired
    private JobSkillRepository jobSkillRepository;

    @Autowired
    private JobSkillMapper jobSkillMapper;

    @Override
    protected IRepository<JobSkill, Long> getRepository() {
        return jobSkillRepository;
    }

    @Override
    protected BaseMapper<JobSkill, JobSkillDTO> getMapper() {
        return jobSkillMapper;
    }
}
