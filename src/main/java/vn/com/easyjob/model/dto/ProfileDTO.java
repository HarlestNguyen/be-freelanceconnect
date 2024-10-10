package vn.com.easyjob.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Builder
@Data
public class ProfileDTO {
    private String fullname;
    private Integer age;
    private String avatar;
    private Boolean isVerified;
    private Integer numOfJob;
    private Double star;
    private Date createdDate;
    private String address;
    private Integer provinceId;
    private Integer districtId;
    private Collection<JobSkillDTO> jobSkills;
}
