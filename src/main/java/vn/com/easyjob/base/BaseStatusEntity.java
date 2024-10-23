package vn.com.easyjob.base;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@MappedSuperclass
@EqualsAndHashCode
public class BaseStatusEntity<T extends Enum> {
    @Id
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private T name;
}
