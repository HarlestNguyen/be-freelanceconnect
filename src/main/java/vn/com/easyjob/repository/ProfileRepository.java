package vn.com.easyjob.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.JobDetail;
import vn.com.easyjob.model.entity.Profile;
import vn.com.easyjob.repository.custom.CustomPagingRepository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends IRepository<Profile, Integer>, JpaSpecificationExecutor<Profile>, CustomPagingRepository<Profile> {
    //    @Query("SELECT p FROM Profile p WHERE p.account.email = :email")
    Optional<Profile> findByAccount_Email(String email);


}
