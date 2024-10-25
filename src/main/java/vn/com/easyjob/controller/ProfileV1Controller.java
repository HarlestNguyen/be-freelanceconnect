package vn.com.easyjob.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.com.easyjob.model.dto.ResponseDTO;
import vn.com.easyjob.model.entity.Profile;
import vn.com.easyjob.service.auth.ProfileService;
import vn.com.easyjob.util.SortUtils;

import java.util.List;

@Tag(name = "profile")
@RestController
@RequestMapping("/api/v1/profile")
@SecurityRequirement(name = "bearer-key")
public class ProfileV1Controller {
    private static final Logger log = LoggerFactory.getLogger(ProfileV1Controller.class);
    @Autowired
    private ProfileService profileService;

    @GetMapping
    public ResponseEntity<?> getProfile(Pageable pageable,
                                        @RequestParam(required = false) Boolean isDel,
                                        @RequestParam(required = false) Integer role){
        // Khởi tạo PageAndKeyword từ các tham số
        Sort sort = pageable.getSort();
        for (Sort.Order order : sort) {
            String field = order.getProperty();
            // Kiểm tra xem field có hợp lệ không
            if (!SortUtils.isValidSortField(Profile.class, field)) {
                // Nếu field không hợp lệ, bỏ qua sort hoặc thiết lập lại sort mặc định
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
                break;
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO("Get all Profile Success",profileService.getProfiles(pageable,role,isDel))
        );
    }

    // API tìm kiếm hồ sơ theo từ khóa và phân trang
    @GetMapping("/search")
    public ResponseEntity<?> getProfileById(
            @RequestParam(required = false) String keywords,
            Pageable pageable) {

        // Khởi tạo PageAndKeyword từ các tham số
        Sort sort = pageable.getSort();
        for (Sort.Order order : sort) {
            String field = order.getProperty();
            // Kiểm tra xem field có hợp lệ không
            if (!SortUtils.isValidSortField(Profile.class, field)) {
                // Nếu field không hợp lệ, bỏ qua sort hoặc thiết lập lại sort mặc định
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
                break;
            }
        }
        // Gọi service để tìm kiếm hồ sơ theo từ khóa và phân trang
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO("Search Profile Success", profileService.sreachProfile(pageable,keywords)));
    }

    @PostMapping("toggleIsDeleted/{id}")
    public ResponseEntity<?> changeStatus(
            @PathVariable int id) {
            profileService.ChangeIsdel(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO("Change Profile Success"));
    }
}
