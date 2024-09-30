package vn.com.freelanceconnect.repository;

import vn.com.freelanceconnect.entity.Account;
import vn.com.freelanceconnect.generic.IRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends IRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);
}
