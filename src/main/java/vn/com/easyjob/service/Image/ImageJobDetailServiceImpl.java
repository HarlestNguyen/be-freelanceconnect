package vn.com.easyjob.service.Image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.com.easyjob.base.BaseAbstractService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.dto.ImageDTO;
import vn.com.easyjob.model.entity.ImageJobDetail;
import vn.com.easyjob.model.entity.JobDetail;
import vn.com.easyjob.repository.ImageJobDetailRepository;
import vn.com.easyjob.repository.JobDetailRepository;
import vn.com.easyjob.service.Cloudiary.CloudinaryService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class ImageJobDetailServiceImpl extends BaseAbstractService<ImageJobDetail, Long> implements ImageJobDetailService {
    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImageJobDetailRepository imageJobDetailRepository;

    @Override
    protected IRepository<ImageJobDetail, Long> getRepository() {
        return imageJobDetailRepository;
    }

    @Autowired
    private JobDetailRepository jobDetailRepository;

    @Override
    public Set<ImageDTO> saveImageOfJobDetail(MultipartFile[] imageOfJobDetail, Long JobDetailID, String typeofImage) throws Exception {
        Set<ImageDTO> imageDTOResponses = new HashSet<>();

        // Tìm kiếm jobDetail một lần, tách biệt logic tìm kiếm khỏi vòng lặp
        JobDetail jobDetail = jobDetailRepository.findById(JobDetailID)
                .orElseThrow(() -> new Exception("Job Not Found"));

        for (MultipartFile file : imageOfJobDetail) {
            // Tạo UUID cho từng ảnh
            String uuid = java.util.UUID.randomUUID().toString();

            // Upload ảnh lên Cloudinary
            Map<String, Object> uploadResult = cloudinaryService.uploadImage(file, uuid);

            // Tạo đối tượng ImageJobDetail
            ImageJobDetail img = new ImageJobDetail();
            img.setJobDetail(jobDetail);
            img.setUrl(uploadResult.get("secure_url").toString());
            img.setCloudiaryPuclicUrl(uuid);  // Đổi tên biến cho đúng chính tả
            img.setTypeOfImg(typeofImage);
            img.setIsDeleted(false);
            // Lưu ImageJobDetail vào repository
            ImageJobDetail savedImg = imageJobDetailRepository.save(img);

            // Tạo ImageDTO và thêm vào tập kết quả
            imageDTOResponses.add(
                    ImageDTO.builder()
                            .url(savedImg.getUrl())
                            .cloudiaryPuclicUrl(savedImg.getCloudiaryPuclicUrl())
                            .typeOfImg(savedImg.getTypeOfImg())
                            .build()
            );
        }

        return imageDTOResponses;
    }
}
