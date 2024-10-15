package vn.com.easyjob.model.entity;

import jakarta.persistence.*;
import lombok.*;
import vn.com.easyjob.base.BaseEntity;

import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "tbl_job_skill")
public class JobSkill extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String skill;
    @Column(columnDefinition = "longtext")
    private String description;
    @ManyToMany(mappedBy = "jobSkills", fetch = FetchType.LAZY)
    private Collection<JobDetail> jobDetails;
    @ManyToMany(mappedBy = "jobSkills", fetch = FetchType.LAZY)
    private Collection<Profile> profiles;
}
