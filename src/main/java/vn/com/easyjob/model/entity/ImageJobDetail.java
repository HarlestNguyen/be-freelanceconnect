package vn.com.easyjob.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import vn.com.easyjob.base.BaseEntity;
import vn.com.easyjob.base.BaseIMG;

@Entity
@Data
@Table(name = "tbl_image_job_detail")
public class ImageJobDetail extends BaseIMG {

    @ManyToOne
    @JoinColumn(name = "job_detail_id", nullable = false)
    private JobDetail jobDetail;

}
