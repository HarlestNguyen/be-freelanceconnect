package vn.com.easyjob.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import vn.com.easyjob.base.CustomPageResponse;
import vn.com.easyjob.model.dto.ResponseDTO;
import vn.com.easyjob.model.entity.Profile;
import vn.com.easyjob.service.auth.ProfileService;
import vn.com.easyjob.util.AuthConstants;
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
    @PreAuthorize(AuthConstants.ADMIN)
    public ResponseEntity<?> searchProfiles(
            @Parameter(description = "Search by Email or FullName")
            @RequestParam(value = "keyword", required = false) String keyword,
            @Parameter(description = "2 => Employer - 3 => Apllier")
            @RequestParam(value = "role", required = false) Integer role,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction", schema = @Schema(allowableValues = {"ASC", "DESC"}))
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "isDeleted", required = false) Boolean isDeleted
    ) {
        // Tạo đối tượng Sort
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        // Kiểm tra tính hợp lệ của trường sortBy, nếu không hợp lệ đặt sort mặc định là "id"
        if (!SortUtils.isValidSortField(Profile.class, sortBy)) {
            sort = Sort.by(Sort.Direction.fromString(direction),"id"); // Đặt sort mặc định là "id"
        }
        // Tạo đối tượng Pageable với Sort đã xử lý
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(profileService.getProfiles(keyword, isDeleted, role, pageable))
        );
    }



    @PostMapping("toggleIsDeleted/{id}")
    @PreAuthorize(AuthConstants.ADMIN)
    public ResponseEntity<?> changeStatus(
            @PathVariable int id) {
            profileService.ChangeIsdel(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO("Change Profile Success"));
    }
}
