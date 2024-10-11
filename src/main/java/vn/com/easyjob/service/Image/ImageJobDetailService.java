package vn.com.easyjob.service.Image;

import org.springframework.web.multipart.MultipartFile;
import vn.com.easyjob.base.IService;
import vn.com.easyjob.model.dto.ImageDTO;
import vn.com.easyjob.model.entity.ImageJobDetail;

import java.util.Set;

public interface ImageJobDetailService extends IService<ImageJobDetail, Long> {
    Set<ImageDTO> saveImageOfJobDetail(MultipartFile[] imageOfJobDetail, Long JobDetailID, String typeofImage) throws Exception;
}
