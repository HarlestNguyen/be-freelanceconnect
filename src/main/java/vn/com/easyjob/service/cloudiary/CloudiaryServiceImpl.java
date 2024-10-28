package vn.com.easyjob.service.cloudiary;


import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.com.easyjob.exception.ErrorHandler;

import java.io.File;
import java.util.*;

@Service
@Lazy
public class CloudiaryServiceImpl implements CloudinaryService {

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    CloudiaryValidationService cloudiaryValidationService;

    @Override
    public Map uploadImage(MultipartFile multipartFile, String uuid) throws Exception {
        if (multipartFile.isEmpty()) throw new ErrorHandler(HttpStatus.NOT_FOUND, "File is empty");
        if (!cloudiaryValidationService.isValid(multipartFile, uuid))
            throw new ErrorHandler(HttpStatus.NOT_FOUND, "ImageError");

        Map param = new HashMap<String, Object>() {
            {
                // Đặt UUID cho file trên Cloudinary, giúp quản lý file với định danh duy nhất.
                put("public_id", uuid);
                // Ghi đè nếu đã có uuid
                put("overwrite", true);
                // Đặt là auto, Cloudinary sẽ tự động nhận diện loại tài nguyên (ảnh, video, v.v.).
                put("resource_type", "auto");
            }
        };
        File fileToUpload = File.createTempFile("temp-file", multipartFile.getOriginalFilename());
        multipartFile.transferTo(fileToUpload);
        Map upload = this.cloudinary.uploader().upload(fileToUpload, param);
        return upload;
    }

    // Phương thức mới để upload mảng MultipartFile
    @Override
    public List<String> uploadImages(ArrayList<MultipartFile> files) throws Exception {
        List<String> urls = new ArrayList<>(); // Danh sách lưu URL kết quả
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String uuid = UUID.randomUUID().toString(); // Tạo UUID cho mỗi file
            if (file.isEmpty()) {
                throw new ErrorHandler(HttpStatus.NOT_FOUND, "File at index " + i + " is empty");
            }
            if (!cloudiaryValidationService.isValid(file, uuid)) {
                throw new ErrorHandler(HttpStatus.NOT_FOUND, "ImageError for file at index " + i);
            }

            Map<String, Object> param = new HashMap<>() {{
                put("public_id", uuid);
                put("overwrite", true);
                put("resource_type", "auto");
            }};

            File fileToUpload = File.createTempFile("temp-file", file.getOriginalFilename());
            file.transferTo(fileToUpload);
            Map uploadResult = this.cloudinary.uploader().upload(fileToUpload, param);
            fileToUpload.delete(); // Xóa file tạm sau khi upload

            // Lấy URL từ kết quả upload và thêm vào danh sách URLs
            String url = (String) uploadResult.get("url");
            urls.add(url);
        }
        return urls; // Trả về danh sách URLs theo thứ tự
    }

    @Override
    public boolean deleteImage(String cloudinaryPublicId) throws Exception {
        if (!cloudiaryValidationService.isValid(cloudinaryPublicId))
            throw new ErrorHandler(HttpStatus.NOT_FOUND, "ImageError");
        ApiResponse apiResponse = this.cloudinary.api()
                .deleteResources(Collections.singletonList(cloudinaryPublicId), new HashMap());

        JSONObject deleted = (JSONObject) apiResponse.get("deleted");
        String deletingResult = deleted.get(cloudinaryPublicId).toString();


        return deletingResult.equals("deleted");
    }
}
