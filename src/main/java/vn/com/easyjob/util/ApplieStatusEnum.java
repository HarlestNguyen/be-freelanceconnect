package vn.com.easyjob.util;

import lombok.Getter;

@Getter
public enum ApplieStatusEnum {
    WAITING(1, "WAITING"),
    ACCEPTED(2, "ACCEPTED"),
    CANCEL_BY_APPLIER(3, "CANCEL_BY_APPLIER"),
    REJECTED(4, "REJECTED"),
    COMPLETED(5, "COMPLETED"),
    IN_TIME(6, "IN_TIME"),
    WORKING(7, "WORKING"),
    CANCEL_BY_EMPLOYER(8, "CANCEL_BY_EMPLOYER");

    private Long id;
    private String name;

    ApplieStatusEnum(Integer id, String name) {
        this.id = id.longValue();
        this.name = name;
    }
}
