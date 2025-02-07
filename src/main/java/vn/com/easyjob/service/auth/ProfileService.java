package vn.com.easyjob.service.auth;

import org.springframework.data.domain.Pageable;
import vn.com.easyjob.base.CustomPageResponse;
import vn.com.easyjob.base.IService;

import vn.com.easyjob.model.dto.CitizenIdentityCardDTO;
import vn.com.easyjob.model.dto.ProfileDTO;
import vn.com.easyjob.model.entity.Profile;
import vn.com.easyjob.model.record.ChangeInfoRecord;
import vn.com.easyjob.model.record.VerificationRecord;


public interface ProfileService extends IService<Profile, Integer> {
    Profile getAuthenticatedProfile();
    ProfileDTO getSelfInformation();
    ProfileDTO updateProfile(ChangeInfoRecord record);
    CustomPageResponse<ProfileDTO> getProfiles( String keyword,Boolean isDeleted,Integer role,Pageable pageable);
    void ChangeIsdel(int id);
    CitizenIdentityCardDTO verifySelf(VerificationRecord record);
}
