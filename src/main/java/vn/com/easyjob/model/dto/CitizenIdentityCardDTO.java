package vn.com.easyjob.model.dto;

import lombok.Builder;
import lombok.Data;
import vn.com.easyjob.util.GenderEnum;

import java.time.LocalDate;

@Builder
@Data
public class CitizenIdentityCardDTO {
    private Integer id;
    private Character no;
    private String fullname;
    private LocalDate dob;
    private GenderEnum gender;
    private String placeOfPrecidence;
    private String frontOfCard;
    private String backOfCard;
    private LocalDate dateOfIssue;
}
