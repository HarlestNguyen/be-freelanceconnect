package vn.com.easyjob.util;

public class AuthConstants {
    public static final String ALL = "authenticated()";
    public static final String TECHNICIAN_MANAGER = "hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_TECHNICIAN')";
    public static final String MANAGER = "hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')";
    public static final String TECHNICIAN = "hasAnyAuthority('ROLE_ADMIN','ROLE_TECHNICIAN')";
    public static final String ADMIN = "hasAnyAuthority('ROLE_ADMIN')";
    public static final String NONE = "permitAll()";
}