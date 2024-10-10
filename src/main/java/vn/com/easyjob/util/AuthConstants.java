package vn.com.easyjob.util;

public class AuthConstants {
    public static final String ALL = "hasAnyAuthority('ROLE_ADMIN','ROLE_EMPLOYER','ROLE_APPLIER')";
    public static final String EMPLOYER = "hasAnyAuthority('ROLE_ADMIN','ROLE_EMPLOYER')";
    public static final String APPLIER = "hasAnyAuthority('ROLE_ADMIN','ROLE_APPLIER')";
    public static final String ADMIN = "hasAnyAuthority('ROLE_ADMIN')";
    public static final String NONE = "permitAll()";
}