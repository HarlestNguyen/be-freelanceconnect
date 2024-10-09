package vn.com.easyjob.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseAbstractService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.ApplieStatus;
import vn.com.easyjob.repository.ApplieStatusRepository;
import vn.com.easyjob.service.ApplieStatusService;

@Service
public class ApplieStatusServiceImpl extends BaseAbstractService<ApplieStatus, Long> implements ApplieStatusService {
    @Autowired
    private ApplieStatusRepository applieStatusRepository;

    @Override
    protected IRepository<ApplieStatus, Long> getRepository() {
        return applieStatusRepository;
    }
}
