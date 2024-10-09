package vn.com.easyjob.repository;

import org.springframework.stereotype.Repository;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.AppliedJob;

@Repository
public interface AppliedJobRepository extends IRepository<AppliedJob, Long> {
}
