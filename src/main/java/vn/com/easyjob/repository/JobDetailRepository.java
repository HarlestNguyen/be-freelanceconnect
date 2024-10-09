package vn.com.easyjob.repository;

import org.springframework.stereotype.Repository;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.JobDetail;

@Repository
public interface JobDetailRepository extends IRepository<JobDetail, Long> {
}
