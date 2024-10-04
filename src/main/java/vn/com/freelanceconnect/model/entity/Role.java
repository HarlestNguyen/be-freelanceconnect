package vn.com.freelanceconnect.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import vn.com.freelanceconnect.base.BaseEntity;
import vn.com.freelanceconnect.util.RoleEnum;

import java.util.Collection;

@Entity
@Data
@Table(name = "tbl_role")
public class Role extends BaseEntity {
    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;
    @Column(columnDefinition = "longtext")
    private String description;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<Account> accounts;
}