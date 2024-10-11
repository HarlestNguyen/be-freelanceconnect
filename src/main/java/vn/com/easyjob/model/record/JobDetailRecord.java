package vn.com.easyjob.model.record;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record JobDetailRecord(
        String title,
        String address,
        String phone,
        Integer districtId,
        Integer provinceId,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT")
        LocalDateTime startDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT")
        LocalDateTime endDate,
        Integer duration,
        String description,

        Long jobTypeId,
        MultipartFile[] imageJobDetails
) {
}
