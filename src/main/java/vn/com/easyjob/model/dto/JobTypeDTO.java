package vn.com.easyjob.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobTypeDTO {
    private Long id;              // ID của loại công việc
    private String name;          // Tên loại công việc
    private String description;   // Mô tả công việc
    private Integer minPrice;     // Giá tối thiểu
    private Integer maxPrice;     // Giá tối đa
}
