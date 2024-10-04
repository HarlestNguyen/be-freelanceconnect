package vn.com.freelanceconnect.repository;

import org.springframework.stereotype.Repository;
import vn.com.freelanceconnect.model.entity.Role;
import vn.com.freelanceconnect.base.IRepository;

@Repository
public interface RoleRepository extends IRepository<Role, Integer> {
}
