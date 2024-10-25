package vn.com.easyjob.model.dto;

import lombok.*;

import java.util.Collection;
import java.util.Date;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileAppliesDTO {
    private Long id;
    private String address;
    private String fullname;
    private Integer age;
    private String avatar;
    private Boolean isVerified;
    private Integer numOfJob;
    private Integer hourOfWork;
    private Double star;
    private Date createdDate;
    private Collection<JobSkillDTO> jobSkills;
}
