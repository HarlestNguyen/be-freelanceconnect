package vn.com.easyjob.repository;

import org.springframework.stereotype.Repository;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.Role;
import vn.com.easyjob.util.RoleEnum;

import java.util.Optional;

@Repository
public interface RoleRepository extends IRepository<Role, Integer> {

    Optional<Role> findByName(RoleEnum name);
}
