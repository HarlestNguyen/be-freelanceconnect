package vn.com.easyjob.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import vn.com.easyjob.base.BaseEntity;
import vn.com.easyjob.base.BaseStatusEntity;
import vn.com.easyjob.util.RoleEnum;

import java.util.Collection;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_role")
public class Role extends BaseStatusEntity<RoleEnum> {
    @Column(columnDefinition = "longtext")
    private String description;
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<Account> accounts;
}