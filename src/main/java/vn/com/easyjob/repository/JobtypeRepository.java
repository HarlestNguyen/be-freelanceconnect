package vn.com.easyjob.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.easyjob.model.entity.JobType;

public interface JobtypeRepository extends JpaRepository<JobType,Long> {
}
