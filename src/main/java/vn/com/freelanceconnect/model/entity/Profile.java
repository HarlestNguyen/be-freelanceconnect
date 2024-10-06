package vn.com.freelanceconnect.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.freelanceconnect.base.BaseAuditableEntity;

import java.sql.Date;
import java.util.Collection;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "tbl_profile")
public class Profile extends BaseAuditableEntity {
    @Column(length = 80, nullable = false)
    private String fullname;
    @Column(nullable = false)
    private String email;
    @Column
    private Date dob;
    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private Collection<Account> accounts;
    @OneToMany(mappedBy = "poster", fetch = FetchType.LAZY)
    private Collection<JobDetail> jobDetails;
    @OneToMany(mappedBy = "applier", fetch = FetchType.LAZY)
    private Collection<AppliedJob> appliedJobs;
}
