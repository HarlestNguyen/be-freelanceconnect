package vn.com.easyjob.repository;

import org.springframework.stereotype.Repository;
import vn.com.easyjob.model.entity.Role;
import vn.com.easyjob.base.IRepository;

@Repository
public interface RoleRepository extends IRepository<Role, Integer> {
}
