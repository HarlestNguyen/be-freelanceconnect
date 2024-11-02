package vn.com.easyjob.model.record;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;

public record ChangeInfoRecord(
        String fullname,
        String phone,
        LocalDate dob,
        MultipartFile avatar,
        String address,
        Integer districtId,
        Integer provinceId,
        Long[] skillIds
) {
}
