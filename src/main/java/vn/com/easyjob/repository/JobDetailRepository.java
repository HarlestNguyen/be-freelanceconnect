package vn.com.easyjob.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import vn.com.easyjob.base.CustomPageResponse;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.JobDetail;
import vn.com.easyjob.repository.Custom.CustomPagingRepository;

@Repository
public interface JobDetailRepository extends IRepository<JobDetail, Long>, CustomPagingRepository<JobDetail> {

    @Override
    CustomPageResponse<JobDetail> findCustomPage(Pageable pageable, Class<JobDetail> entityClass);
}
