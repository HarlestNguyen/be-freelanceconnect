package vn.com.easyjob.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseAbstractService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.Role;
import vn.com.easyjob.repository.RoleRepository;
import vn.com.easyjob.service.RoleService;

@Service
public class RoleServiceImpl extends BaseAbstractService<Role, Integer> implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    protected IRepository<Role, Integer> getRepository() {
        return roleRepository;
    }
}
