package vn.com.easyjob.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JobSkillDTO {
    private Long id;
    private String skill;
    private String description;
}
