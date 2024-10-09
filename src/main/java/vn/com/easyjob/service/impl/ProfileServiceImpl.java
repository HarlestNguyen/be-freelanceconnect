package vn.com.easyjob.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseAbstractService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.Profile;
import vn.com.easyjob.repository.ProfileRepository;
import vn.com.easyjob.service.ProfileService;

@Service
public class ProfileServiceImpl extends BaseAbstractService<Profile, Integer> implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    protected IRepository<Profile, Integer> getRepository() {
        return profileRepository;
    }
}
