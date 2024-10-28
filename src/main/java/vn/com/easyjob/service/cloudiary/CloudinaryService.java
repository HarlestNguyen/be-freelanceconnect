package vn.com.easyjob.service.cloudiary;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CloudinaryService {
    Map uploadImage(MultipartFile multipartFile, String uuid) throws Exception;
    List<String> uploadImages(ArrayList<MultipartFile> files) throws Exception;
    boolean deleteImage(String cloudinaryPublicId) throws Exception;
}
