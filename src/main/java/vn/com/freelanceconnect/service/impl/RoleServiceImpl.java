package vn.com.freelanceconnect.service.impl;

import vn.com.freelanceconnect.generic.IRepository;
import vn.com.freelanceconnect.repository.RoleRepository;
import vn.com.freelanceconnect.service.RoleService;
import vn.com.freelanceconnect.entity.Role;
import vn.com.freelanceconnect.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends AbstractService<Role, Integer> implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    protected IRepository<Role, Integer> getRepository() {
        return roleRepository;
    }
}
