package vn.com.freelanceconnect.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import vn.com.freelanceconnect.base.BaseAuditableEntity;
import vn.com.freelanceconnect.util.ApplieStatusEnum;

@Entity
@Data
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
