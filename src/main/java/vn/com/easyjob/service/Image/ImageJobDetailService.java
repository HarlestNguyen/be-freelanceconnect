package vn.com.easyjob.service.Image;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.com.easyjob.model.dto.ImageDTO;

import java.util.Set;

@Service
public interface ImageJobDetailService {
    Set<ImageDTO> saveImageOfJobDetail(MultipartFile[] imageOfJobDetail, Long JobDetailID, String typeofImage) throws Exception;

}
