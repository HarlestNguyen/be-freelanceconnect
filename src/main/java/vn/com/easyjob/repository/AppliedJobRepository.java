package vn.com.easyjob.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.AppliedJob;
import vn.com.easyjob.model.entity.Profile;
import vn.com.easyjob.util.ApplieStatusEnum;

@Repository
public interface AppliedJobRepository extends IRepository<AppliedJob, Long> {

    @Query("select p.applier from AppliedJob p where p.applieStatus.name = :applieStatus and p.jobDetail.id = :jobid")
    Page<Profile> findByJobDetailId(@Param("applieStatus") ApplieStatusEnum applieStatusEnum,
                                    @Param("jobid") long jobid,
                                    Pageable pageable);
}
