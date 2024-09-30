package vn.com.freelanceconnect.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data
@MappedSuperclass
public abstract class EntityDefine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(name = "is_deleted", nullable = false)
    @ColumnDefault("b'0'")
    private Boolean isDeleted;
}
