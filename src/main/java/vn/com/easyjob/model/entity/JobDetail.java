package vn.com.easyjob.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.easyjob.base.BaseAuditableEntity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_job_detail")
@DynamicInsert
@DynamicUpdate
public class JobDetail extends BaseAuditableEntity {
    @Column(length = 100)
    private String title;
    @Column(length = 100)
    private String address;
    @Column(length = 10, nullable = false)
    private String phone;
    @Column(name = "district_id", nullable = false)
    private Integer districtId;
    @Column(name = "province_id", nullable = false)
    private Integer provinceId;
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;
    @Column(nullable = false)
    @Max(8 * 60)
    private Integer duration;
    @Column(columnDefinition = "longtext")
    private String description;
    @ManyToOne
    @JoinColumn(name = "poster", nullable = false)
    private Profile poster;
    @ManyToOne
    @JoinColumn(name = "job_type_id", nullable = false)
    private JobType jobType;
    @ManyToOne
    @JoinColumn(name = "job_approval_status_id", nullable = false, columnDefinition = "BIGINT DEFAULT 1")
    private JobApprovalStatus jobApprovalStatus;
    @OneToMany(mappedBy = "jobDetail", fetch = FetchType.EAGER)
    private Collection<ImageJobDetail> imageJobDetails;
    @OneToMany(mappedBy = "jobDetail", fetch = FetchType.LAZY)
    private Collection<AppliedJob> jobApplies;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tbl_job_available_skill",
            joinColumns = @JoinColumn(name = "job_detail_id"),
            inverseJoinColumns = @JoinColumn(name = "job_skill_id")
    )
    private Set<JobSkill> jobSkills;
}
