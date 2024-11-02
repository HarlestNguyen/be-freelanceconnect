package vn.com.easyjob.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@MappedSuperclass
public abstract class BaseIMG extends BaseEntity {

    @Column(nullable = false)
    protected String url;

    @Column(nullable = false)
    protected String cloudiaryPuclicUrl;

    @Column(nullable = false)
    protected String TypeOfImg;
}
