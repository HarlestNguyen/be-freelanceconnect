package vn.com.easyjob.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.easyjob.model.entity.JobApprovalStatus;
import vn.com.easyjob.util.JobApprovalStatusEnum;

import java.util.Optional;

@Repository
public interface JobApprovalStatusRepository extends JpaRepository<JobApprovalStatus, Long> {
    Optional<JobApprovalStatus> findByName(JobApprovalStatusEnum jobApprovalStatusEnum);
}
