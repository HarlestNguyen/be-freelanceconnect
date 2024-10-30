package vn.com.easyjob.util;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum JobApprovalStatusEnum {
    ALL(4, "ALL"),
    PENDING(1, "PENDING"),
    REJECTED(2, "REJECTED"),
    APPROVED(3, "APPROVED");

    private Long id;
    private String name;

    JobApprovalStatusEnum(Integer id, String name) {
        this.id = id.longValue();
        this.name = name;
    }
}
