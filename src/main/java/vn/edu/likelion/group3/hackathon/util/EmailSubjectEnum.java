package vn.edu.likelion.group3.hackathon.util;

public enum EmailSubjectEnum {
    BASIC("Email From The Third Team"),
    OTP("Email Otp Verification From The Third Team"),
    LINK("Email Link Verification From The Third Team");

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
