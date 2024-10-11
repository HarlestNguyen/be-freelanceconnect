package vn.com.easyjob.base;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseIMG extends BaseEntity {

    @Column(nullable = false)
    protected String url;

    @Column(nullable = false)
    protected String cloudiaryPuclicUrl;

    @Column(nullable = false)
    protected String TypeOfImg;
}
