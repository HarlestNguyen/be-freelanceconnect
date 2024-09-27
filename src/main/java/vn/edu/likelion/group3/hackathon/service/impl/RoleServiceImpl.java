package vn.edu.likelion.group3.hackathon.service.impl;

import vn.edu.likelion.group3.hackathon.entity.Role;
import vn.edu.likelion.group3.hackathon.generic.IRepository;
import vn.edu.likelion.group3.hackathon.repository.RoleRepository;
import vn.edu.likelion.group3.hackathon.service.AbstractService;
import vn.edu.likelion.group3.hackathon.service.RoleService;
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
