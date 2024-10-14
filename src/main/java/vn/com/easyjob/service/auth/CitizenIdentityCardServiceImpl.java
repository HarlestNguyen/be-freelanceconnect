package vn.com.easyjob.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.CitizenIdentityCard;
import vn.com.easyjob.repository.CitizenIdentityCardRepository;

@Service
public class CitizenIdentityCardServiceImpl extends BaseService<CitizenIdentityCard, Long> implements CitizenIdentityCardService {
    @Autowired
    private CitizenIdentityCardRepository citizenIdentityCardRepository;

    @Override
    protected IRepository<CitizenIdentityCard, Long> getRepository() {
        return citizenIdentityCardRepository;
    }
}
