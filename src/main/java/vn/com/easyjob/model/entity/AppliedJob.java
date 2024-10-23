package vn.com.easyjob.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import vn.com.easyjob.base.BaseAuditableEntity;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_job_applie")
public class AppliedJob extends BaseAuditableEntity {
    @ManyToOne
    @JoinColumn(name = "applier", nullable = false)
    private Profile applier;
    @ManyToOne
    @JoinColumn(name = "job_detail", nullable = false)
    private JobDetail jobDetail;
    @ManyToOne
    @JoinColumn(name = "applie_status_id", nullable = false)
    private ApplieStatus applieStatus;
}
