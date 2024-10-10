package vn.com.easyjob.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.Profile;

@Repository
public interface ProfileRepository extends IRepository<Profile, Integer> {
    @Query("SELECT p FROM Profile p WHERE p.account.email = :email")
    Profile findByEmail(@Param("email") String email);
}
