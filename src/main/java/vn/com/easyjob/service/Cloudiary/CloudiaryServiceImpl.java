package vn.com.easyjob.service.Cloudiary;


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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Lazy
public class CloudiaryServiceImpl implements CloudinaryService {

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    CloudiaryValidationService cloudiaryValidationService;

    @Override
    public Map uploadImage(MultipartFile multipartFile, String uuid) throws Exception {
       if(multipartFile.isEmpty()) throw  new ErrorHandler(HttpStatus.NOT_FOUND,"File is empty");
       if (!cloudiaryValidationService.isValid(multipartFile, uuid)) throw new ErrorHandler(HttpStatus.NOT_FOUND,"ImageError");

       Map param = new HashMap<String,Object>(){
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

    @Override
    public boolean deleteImage(String cloudinaryPublicId) throws Exception {
        if(!cloudiaryValidationService.isValid(cloudinaryPublicId)) throw new ErrorHandler(HttpStatus.NOT_FOUND,"ImageError");
      ApiResponse apiResponse = this.cloudinary.api()
                .deleteResources(Collections.singletonList(cloudinaryPublicId), new HashMap());

        JSONObject deleted = (JSONObject) apiResponse.get("deleted");
        String deletingResult = deleted.get(cloudinaryPublicId).toString();


        return deletingResult.equals("deleted");
    }
}
