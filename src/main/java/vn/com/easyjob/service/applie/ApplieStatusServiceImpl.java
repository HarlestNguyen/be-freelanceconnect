package vn.com.easyjob.service.applie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.ApplieStatus;
import vn.com.easyjob.repository.ApplieStatusRepository;

@Service
public class ApplieStatusServiceImpl extends BaseService<ApplieStatus, Long> implements ApplieStatusService {
    @Autowired
    private ApplieStatusRepository applieStatusRepository;

    @Override
    protected IRepository<ApplieStatus, Long> getRepository() {
        return applieStatusRepository;
    }
}
