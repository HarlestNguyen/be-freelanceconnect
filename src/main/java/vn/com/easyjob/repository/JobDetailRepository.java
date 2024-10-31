package vn.com.easyjob.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.JobDetail;
import vn.com.easyjob.repository.custom.CustomPagingRepository;
import vn.com.easyjob.util.JobApprovalStatusEnum;

@Repository
public interface JobDetailRepository extends IRepository<JobDetail, Long>, JpaSpecificationExecutor<JobDetail>, CustomPagingRepository<JobDetail> {
    boolean existsByPosterId(Long posterId);
    Page<JobDetail> findByPosterIdAndJobApprovalStatusName(Long posterId
                                                            ,Pageable pageable, JobApprovalStatusEnum approvalStatusEnum);
}