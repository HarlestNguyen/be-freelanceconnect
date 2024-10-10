package vn.com.easyjob.repository;

import org.springframework.stereotype.Repository;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.JobType;

@Repository
public interface JobTypeRepository extends IRepository<JobType, Long> {
}
