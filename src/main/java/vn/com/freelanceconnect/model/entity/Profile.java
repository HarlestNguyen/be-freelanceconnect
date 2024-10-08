package vn.com.freelanceconnect.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
    @OneToOne(mappedBy = "profile")
    @PrimaryKeyJoinColumn
    private Account account;
    @OneToOne(mappedBy = "profile")
    @PrimaryKeyJoinColumn
    private CitizenIdentityCard citizenIdentityCard;
    private Date dob;
    private String avatar;
    @OneToMany(mappedBy = "poster", fetch = FetchType.LAZY)
    private Collection<JobDetail> jobDetails;
    @OneToMany(mappedBy = "applier", fetch = FetchType.LAZY)
    private Collection<AppliedJob> appliedJobs;
}
