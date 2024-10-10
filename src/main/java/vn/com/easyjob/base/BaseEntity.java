package vn.com.easyjob.base;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

@Data
@MappedSuperclass
@EqualsAndHashCode
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(name = "is_deleted", nullable = false)
    @ColumnDefault("b'0'")
    private Boolean isDeleted;
}
