package vn.com.easyjob.service.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.JobType;
import vn.com.easyjob.repository.JobTypeRepository;

@Service
public class JobTypeServiceImpl extends BaseService<JobType, Long> implements JobTypeService {
    @Autowired
    private JobTypeRepository jobTypeRepository;

    @Override
    protected IRepository<JobType, Long> getRepository() {
        return jobTypeRepository;
    }
}
