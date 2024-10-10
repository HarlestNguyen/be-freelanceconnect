package vn.com.easyjob.repository;

import org.springframework.stereotype.Repository;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.CitizenIdentityCard;

@Repository
public interface CitizenIdentityCardRepository extends IRepository<CitizenIdentityCard, Long> {
}
