package vn.com.easyjob.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.com.easyjob.base.BaseEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "tbl_account")
@DynamicInsert
public class Account extends BaseEntity implements Serializable, UserDetails {
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @ManyToOne
    @JoinColumn(nullable = false, name = "role_id")
    private Role role;
    @OneToOne
    @MapsId
    @JoinColumn(nullable = false, name = "id")
    private Profile profile;

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) {
            return Collections.emptyList();
        }
        return Collections.singleton(new SimpleGrantedAuthority(role.getName().getRoleName()));
    }



}
