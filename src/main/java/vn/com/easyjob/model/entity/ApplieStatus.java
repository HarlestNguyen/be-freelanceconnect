package vn.com.easyjob.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import vn.com.easyjob.base.BaseEntity;

import java.util.Collection;

@Entity
@Data
@Table(name = "tbl_applie_status")
public class ApplieStatus extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @Column(columnDefinition = "longtext")
    private String desciption;
    @OneToMany(mappedBy = "applieStatus")
    private Collection<AppliedJob> appliedJobs;
}
