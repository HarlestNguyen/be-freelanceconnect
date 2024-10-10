package vn.com.easyjob.model.record;

import org.springframework.web.multipart.MultipartFile;
import vn.com.easyjob.model.dto.ImageDTO;

import java.util.Collection;
import java.util.Date;

public record JobDetailRequest(
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
