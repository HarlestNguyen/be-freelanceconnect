package vn.com.easyjob.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import vn.com.easyjob.base.BaseEntity;
import vn.com.easyjob.util.GenderEnum;

import java.sql.Date;

@Entity
@Data
@Table(name = "tbl_citizen_identity_card")
public class CitizenIdentityCard extends BaseEntity {
    @OneToOne
    @MapsId
    @JoinColumn(nullable = false, name = "id")
    private Profile profile;
    @Column(nullable = false, length = 12)
    private Character no;
    @Column(nullable = false)
    private String fullname;
    @Column(nullable = false, name = "dob")
    private Date dateOfBirth;
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
    private Date dateOfIssue;
}
