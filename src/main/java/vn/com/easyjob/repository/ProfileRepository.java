package vn.com.easyjob.repository;

import org.springframework.stereotype.Repository;
import vn.com.easyjob.model.entity.Profile;
import vn.com.easyjob.base.IRepository;

@Repository
public interface ProfileRepository extends IRepository<Profile, Integer> {
}
