package vn.com.freelanceconnect.repository;

import vn.com.freelanceconnect.entity.Role;
import vn.com.freelanceconnect.generic.IRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends IRepository<Role, Integer> {
}
