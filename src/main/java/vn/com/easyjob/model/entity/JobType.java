package vn.com.easyjob.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.easyjob.base.BaseEntity;

import java.util.Collection;

@Entity
@Data
@Table(name = "tbl_job_type")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class JobType extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @Column(columnDefinition = "longtext")
    private String description;
    @Column(name = "min_price", nullable = false)
    private Integer minPrice;
    @Column(name = "max_price", nullable = false)
    private Integer maxPrice;
    @OneToMany(mappedBy = "jobType")
    private Collection<JobDetail> jobDetails;
}
