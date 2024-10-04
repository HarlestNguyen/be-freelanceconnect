package vn.com.freelanceconnect.repository;

import org.springframework.stereotype.Repository;
import vn.com.freelanceconnect.model.entity.Profile;
import vn.com.freelanceconnect.base.IRepository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends IRepository<Profile, Integer> {
    Optional<Profile> findByEmail(String email);
}
