package vn.com.easyjob.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class JobDTO {

    private Long jobId;               // ID công việc
    private String title;             // Tiêu đề công việc
    private String address;           // Địa chỉ nơi làm việc
    private String phone;             // Số điện thoại liên hệ
    private String contactPerson;     // Người liên hệ
    private LocalDateTime startDate;  // Ngày giờ bắt đầu làm việc
    private LocalDateTime endDate;      // Ngày giờ ngưng nhận việc
    private Integer duration;         // Thời gian làm việc (giờ)
    private JobTypeDTO jobType;       // Loại công việc (bao gồm ID và tên)
    private List<ImageDTO> images;  // Danh sách hình ảnh của công việc
    private String description;       // Mô tả công việc
    private Date postedDate; // Ngày đăng công việc
    private Boolean verified;         // Trạng thái xác thực công việc  // Danh sách các đường dẫn ảnh của công việc

}
