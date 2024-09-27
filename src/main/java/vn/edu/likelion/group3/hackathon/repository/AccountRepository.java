package vn.edu.likelion.group3.hackathon.repository;

import vn.edu.likelion.group3.hackathon.entity.Account;
import vn.edu.likelion.group3.hackathon.generic.IRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends IRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);
}
