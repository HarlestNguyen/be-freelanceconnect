package vn.com.easyjob.model.entity;

import jakarta.persistence.*;
import lombok.*;
import vn.com.easyjob.base.BaseEntity;
import vn.com.easyjob.base.BaseStatusEntity;
import vn.com.easyjob.util.ApplieStatusEnum;

import java.util.Collection;

@Entity
@Data
@ToString(callSuper = true, exclude = {"appliedJobs"})
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_applie_status")
public class ApplieStatus extends BaseStatusEntity<ApplieStatusEnum> {
    @Column(columnDefinition = "longtext")
    private String description;
    @OneToMany(mappedBy = "applieStatus", fetch = FetchType.LAZY)
    private Collection<AppliedJob> appliedJobs;
}
