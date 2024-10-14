package vn.com.easyjob.service.auth;

import vn.com.easyjob.base.IService;
import vn.com.easyjob.model.dto.ProfileDTO;
import vn.com.easyjob.model.entity.Profile;

public interface ProfileService extends IService<Profile, Integer> {
    Profile getAuthenticatedProfile();
    ProfileDTO getSelfInformation();
}
