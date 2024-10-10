package vn.com.easyjob.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.easyjob.base.CustomPageResponse;
import vn.com.easyjob.model.entity.JobDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.com.easyjob.repository.Custom.CustomPagingRepository;

@Repository
public interface JobDetailRepository extends JpaRepository<JobDetail, Long>, CustomPagingRepository<JobDetail> {

    @Override
    CustomPageResponse<JobDetail> findCustomPage(Pageable pageable, Class<JobDetail> entityClass);
}
