package vn.com.easyjob.service.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseAbstractService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.JobDetail;
import vn.com.easyjob.repository.JobDetailRepository;
import vn.com.easyjob.service.JobDetailService;

@Service
public class JobDetailServiceImpl extends BaseAbstractService<JobDetail, Long> implements JobDetailService {
    @Autowired
    private JobDetailRepository jobDetailRepository;

    @Override
    protected IRepository<JobDetail, Long> getRepository() {
        return jobDetailRepository;
    }
}
