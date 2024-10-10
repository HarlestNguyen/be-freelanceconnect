package vn.com.easyjob.model.record;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public record JobDetailRecord(
        String title,
        String address,
        String phone,
        Integer districtId,
        Integer provinceId,
        Date startDate,
        Date endDate,
        Integer duration,
        String description,

        Long jobTypeId,
        MultipartFile[] imageJobDetails
) {
}
