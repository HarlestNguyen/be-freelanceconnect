package vn.com.easyjob.repository;

import org.springframework.stereotype.Repository;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.Profile;

import java.util.Optional;

@Repository
public interface ProfileRepository extends IRepository<Profile, Integer> {
    //    @Query("SELECT p FROM Profile p WHERE p.account.email = :email")
    Optional<Profile> findByAccount_Email(String email);
}
