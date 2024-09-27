package vn.edu.likelion.group3.hackathon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
@Table(name = "tbl_role")
public class Role extends EntityDefine {
    @Column(unique = true, nullable = false)
    private String name;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<Account> accounts;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "tbl_role_permission",
//            joinColumns = @JoinColumn(name = "role_id"),
//            inverseJoinColumns = @JoinColumn(name = "permission_id")
//    )
//    private Collection<Permission> permissions;
}