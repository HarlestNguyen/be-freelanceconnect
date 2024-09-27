package vn.edu.likelion.group3.hackathon.repository;

import vn.edu.likelion.group3.hackathon.entity.Role;
import vn.edu.likelion.group3.hackathon.generic.IRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends IRepository<Role, Integer> {
}
