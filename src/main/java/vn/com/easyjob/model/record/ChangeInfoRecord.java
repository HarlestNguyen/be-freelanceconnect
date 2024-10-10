package vn.com.easyjob.model.record;

import org.springframework.web.multipart.MultipartFile;

public record ChangeInfoRecord(
        String fullname,
        String dob,
        MultipartFile avatar,
        String address,
        Integer districtId,
        Integer provinceId,
        Integer[] skillIds
) {
}
