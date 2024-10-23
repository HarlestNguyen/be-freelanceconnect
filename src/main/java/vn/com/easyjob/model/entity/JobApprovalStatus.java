package vn.com.easyjob.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import vn.com.easyjob.base.BaseEntity;
import vn.com.easyjob.base.BaseStatusEntity;
import vn.com.easyjob.util.JobApprovalStatusEnum;

import java.util.Collection;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_job_approval_status")
public class JobApprovalStatus extends BaseStatusEntity<JobApprovalStatusEnum> {
    @Column(columnDefinition = "longtext")
    private String description;
    @OneToMany(mappedBy = "jobApprovalStatus")
    private Collection<JobDetail> jobDetails;
}
