package vn.com.easyjob.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.easyjob.base.BaseEntity;

import java.util.Collection;

@Entity
@Data
@ToString(callSuper = true, exclude = {"profiles", "jobDetails"})
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_job_skill")
@DynamicInsert
@DynamicUpdate
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
