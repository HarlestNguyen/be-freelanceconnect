package vn.com.easyjob.base;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public abstract class BaseIMG extends BaseEntity {

    @Column(nullable = false)
    protected String url;

    @Column(nullable = false)
    protected String cloudiaryPuclicUrl;

    @Column(nullable = false)
    protected String TypeOfImg;
}
