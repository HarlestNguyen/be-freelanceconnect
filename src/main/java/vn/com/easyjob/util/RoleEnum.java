package vn.com.easyjob.util;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ROLE_ADMIN(1, "ROLE_ADMIN"),
    ROLE_EMPLOYER(2, "ROLE_EMPLOYER"),
    ROLE_APPLIER(3, "ROLE_APPLIER");
    private final Long id;
    private final String roleName;

    RoleEnum(Integer id, String roleName) {
        this.id = id.longValue();
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return roleName;
    }
}
