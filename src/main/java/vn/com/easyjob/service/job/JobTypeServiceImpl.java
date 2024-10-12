package vn.com.easyjob.service.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseMapper;
import vn.com.easyjob.base.BaseService;
import vn.com.easyjob.base.BaseServiceDTO;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.dto.JobTypeDTO;
import vn.com.easyjob.model.entity.JobType;
import vn.com.easyjob.model.mapper.JobTypeMapper;
import vn.com.easyjob.repository.JobTypeRepository;

@Service
public class JobTypeServiceImpl extends BaseServiceDTO<JobType, JobTypeDTO, Long> implements JobTypeService {

    @Autowired
    private JobTypeRepository jobTypeRepository;

    @Autowired
    private JobTypeMapper jobTypeMapper;

    @Override
    protected IRepository<JobType, Long> getRepository() {
        return jobTypeRepository;
    }

    @Override
    protected BaseMapper<JobType, JobTypeDTO> getMapper() {
        return jobTypeMapper;
    }
}

