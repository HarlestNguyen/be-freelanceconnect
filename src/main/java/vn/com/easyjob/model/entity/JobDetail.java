package vn.com.easyjob.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import vn.com.easyjob.base.BaseAuditableEntity;

import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "tbl_job_detail")
@DynamicInsert
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    private Date startDate;
    @Column(name = "end_date", nullable = false)
    private Date endDate;
    @Column(nullable = false)
    @Max(8 * 60)
    private Integer duration;
    @Column(columnDefinition = "longtext")
    private String desciption;
    @ManyToOne
    @JoinColumn(name = "poster", nullable = false)
    private Profile poster;
    @ManyToOne
    @JoinColumn(name = "job_type_id", nullable = false)
    private JobType jobType;
    @OneToMany(mappedBy = "jobDetail", fetch = FetchType.EAGER)
    private Collection<ImageJobDetail> imageJobDetails;
    @OneToMany(mappedBy = "jobDetail", fetch = FetchType.LAZY)
    private Collection<AppliedJob> jobApplies;


}
