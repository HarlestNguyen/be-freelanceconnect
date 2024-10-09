package vn.com.easyjob.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseAbstractService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.JobType;
import vn.com.easyjob.repository.JobTypeRepository;
import vn.com.easyjob.service.JobTypeService;

@Service
public class JobTypeServiceImpl extends BaseAbstractService<JobType, Long> implements JobTypeService {
    @Autowired
    private JobTypeRepository jobTypeRepository;

    @Override
    protected IRepository<JobType, Long> getRepository() {
        return jobTypeRepository;
    }
}
