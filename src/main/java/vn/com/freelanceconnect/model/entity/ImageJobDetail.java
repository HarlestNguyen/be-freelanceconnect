package vn.com.freelanceconnect.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import vn.com.freelanceconnect.base.BaseEntity;

@Entity
@Data
@Table(name = "tbl_image_job_detail")
public class ImageJobDetail extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "job_detail_id", nullable = false)
    private JobDetail jobDetail;
    @Column(name = "cloudinary_public_id", nullable = false)
    private String cloudinaryPublicId;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;
    @Column(name = "type_of_image", nullable = false)
    private String typeOfImage;
}
