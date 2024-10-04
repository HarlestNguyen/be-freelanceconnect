package vn.com.freelanceconnect.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import vn.com.freelanceconnect.base.BaseAuditableEntity;

@Entity
@Data
@Table(name = "tbl_rating")
public class Rating extends BaseAuditableEntity {
}
