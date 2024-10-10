package vn.com.easyjob.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageDTO {
    String url;
    String cloudiaryPuclicUrl;
    String typeOfImg;
}
