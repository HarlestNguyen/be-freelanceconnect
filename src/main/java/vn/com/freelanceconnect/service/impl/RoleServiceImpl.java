package vn.com.freelanceconnect.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.freelanceconnect.model.entity.Role;
import vn.com.freelanceconnect.base.IRepository;
import vn.com.freelanceconnect.repository.RoleRepository;
import vn.com.freelanceconnect.base.BaseAbstractService;
import vn.com.freelanceconnect.service.RoleService;

@Service
public class RoleServiceImpl extends BaseAbstractService<Role, Integer> implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    protected IRepository<Role, Integer> getRepository() {
        return roleRepository;
    }
}
