package vn.com.freelanceconnect.util;

public enum RoleEnum {
    ADMIN(1, "ROLE_ADMIN"),
    BUYER(2, "ROLE_BUYER");
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
