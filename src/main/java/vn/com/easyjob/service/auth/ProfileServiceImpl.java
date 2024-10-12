package vn.com.easyjob.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.exception.ErrorHandler;
import vn.com.easyjob.model.entity.Profile;
import vn.com.easyjob.repository.ProfileRepository;

@Service
public class ProfileServiceImpl extends BaseService<Profile, Integer> implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    protected IRepository<Profile, Integer> getRepository() {
        return profileRepository;
    }

    // Phương thức lấy thông tin người dùng đã đăng nhập và trả về Profile
    @Override
    public Profile getAuthenticatedProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("User is not authenticated");
        }
        String email = authentication.getName();
        return profileRepository.findByAccount_Email(email).orElseThrow(() -> new ErrorHandler(HttpStatus.NOT_FOUND, "Profile Not found"));
    }
}
