package vn.com.easyjob.util;


public enum TypeMailEnum {
    OTP("templates/opt-mail-template.html"),
    VERIFY_LINK("templates/verify-link-mail-template.html"),
    PASSWORD("templates/password-mail-template.html");;

    private final String template;

    TypeMailEnum(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }
}
