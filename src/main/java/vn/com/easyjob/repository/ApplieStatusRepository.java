package vn.com.easyjob.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.easyjob.model.entity.ApplieStatus;
import vn.com.easyjob.util.ApplieStatusEnum;

import java.util.Optional;

public interface ApplieStatusRepository extends JpaRepository<ApplieStatus,Long> {


    Optional<ApplieStatus> findByName(ApplieStatusEnum applieStatusEnum);
}
