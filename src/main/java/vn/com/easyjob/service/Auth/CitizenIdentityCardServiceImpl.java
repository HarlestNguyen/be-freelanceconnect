package vn.com.easyjob.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseAbstractService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.CitizenIdentityCard;
import vn.com.easyjob.repository.CitizenIdentityCardRepository;
import vn.com.easyjob.service.CitizenIdentityCardService;

@Service
public class CitizenIdentityCardServiceImpl extends BaseAbstractService<CitizenIdentityCard, Long> implements CitizenIdentityCardService {
    @Autowired
    private CitizenIdentityCardRepository citizenIdentityCardRepository;

    @Override
    protected IRepository<CitizenIdentityCard, Long> getRepository() {
        return citizenIdentityCardRepository;
    }
}
