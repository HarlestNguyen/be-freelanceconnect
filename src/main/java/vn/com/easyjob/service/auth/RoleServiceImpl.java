package vn.com.easyjob.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.Role;
import vn.com.easyjob.repository.RoleRepository;

@Service
public class RoleServiceImpl extends BaseService<Role, Integer> implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    protected IRepository<Role, Integer> getRepository() {
        return roleRepository;
    }
}
