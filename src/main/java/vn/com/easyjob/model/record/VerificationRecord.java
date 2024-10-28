package vn.com.easyjob.model.record;

import org.springframework.web.multipart.MultipartFile;
import vn.com.easyjob.util.GenderEnum;

public record VerificationRecord(
        MultipartFile frontOfCard,
        MultipartFile backOfCard,
        String no,
        String fullname,
        String dateOfBirth,
        GenderEnum gender,
        String placeOfPrecidence,
        String dateOfIssue
) {
}
