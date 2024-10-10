package vn.com.easyjob.service.applie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseAbstractService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.AppliedJob;
import vn.com.easyjob.repository.AppliedJobRepository;

@Service
public class AppliedJobServiceImpl extends BaseAbstractService<AppliedJob, Long> implements AppliedJobService {

    @Autowired
    private AppliedJobRepository appliedJobRepository;

    @Override
    protected IRepository<AppliedJob, Long> getRepository() {
        return appliedJobRepository;
    }
}
