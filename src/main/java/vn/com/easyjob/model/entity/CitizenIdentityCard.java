package vn.com.easyjob.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.easyjob.base.BaseEntity;
import vn.com.easyjob.util.GenderEnum;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_citizen_identity_card")
@DynamicInsert
@DynamicUpdate
public class CitizenIdentityCard extends BaseEntity {
    @OneToOne
    @MapsId
    @JoinColumn(nullable = false, name = "id")
    private Profile profile;
    @Column(nullable = false, length = 12)
    private String no;
    @Column(nullable = false)
    private String fullname;
    @Column(nullable = false, name = "dob")
    private LocalDate dob;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    @Column(nullable = false, name = "place_of_precidence")
    private String placeOfPrecidence;
    @Column(nullable = false, name = "front_of_card")
    private String frontOfCard;
    @Column(nullable = false, name = "back_of_card")
    private String backOfCard;
    @Column(nullable = false, name = "date_of_issue")
    private LocalDate dateOfIssue;
}
