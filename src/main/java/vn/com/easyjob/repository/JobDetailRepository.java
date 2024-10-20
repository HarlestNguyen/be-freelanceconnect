package vn.com.easyjob.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.JobDetail;
import vn.com.easyjob.repository.custom.CustomPagingRepository;

@Repository
public interface JobDetailRepository extends IRepository<JobDetail, Long>, JpaSpecificationExecutor<JobDetail>, CustomPagingRepository<JobDetail> {

}