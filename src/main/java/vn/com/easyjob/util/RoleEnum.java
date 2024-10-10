package vn.com.easyjob.util;

public enum RoleEnum {
    ROLE_ADMIN(1L, "ROLE_ADMIN"),
    ROLE_EMPLOYER(2L, "ROLE_EMPLOYER"),
    ROLE_APPLIER(3L, "ROLE_APPLIER");
    private final Long id;
    private final String roleName;

    RoleEnum(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return roleName;
    }
}
