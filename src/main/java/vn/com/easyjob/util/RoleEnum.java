package vn.com.easyjob.util;

public enum RoleEnum {
    ROLE_ADMIN(1, "ROLE_ADMIN"),
    ROLE_EMPLOYER(2, "ROLE_EMPLOYER"),
    ROLE_APPLIER(3, "ROLE_APPLIER");
    private final int id;
    private final String roleName;

    RoleEnum(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return roleName;
    }
}
