package vn.com.easyjob.repository;

import org.springframework.stereotype.Repository;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends IRepository<Account, Integer> {
    Optional<Account> findByEmail(String email);
}
