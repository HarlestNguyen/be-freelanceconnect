package vn.com.easyjob.model.record;

import jakarta.validation.constraints.NotNull;

public record JobSkillRecord(
        @NotNull
        String skill,
        String description
) {
}
