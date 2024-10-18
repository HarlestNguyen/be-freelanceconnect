package vn.com.easyjob.util;

public enum EmailSubjectEnum {
    BASIC("Email From The EasyJob Team"),
    OTP("Email OTP Verification From EasyJob"),
    LINK("Email Link Verification From EasyJob"),
    PASSWORD("Email Password Reset From EasyJob");

    private final String subject;

    EmailSubjectEnum(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return subject;
    }
}
