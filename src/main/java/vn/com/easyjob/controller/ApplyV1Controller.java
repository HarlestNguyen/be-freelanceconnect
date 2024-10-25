package vn.com.easyjob.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.easyjob.model.dto.ResponseDTO;
import vn.com.easyjob.model.entity.Profile;
import vn.com.easyjob.service.applie.AppliedJobService;
import vn.com.easyjob.util.ApplieStatusEnum;
import vn.com.easyjob.util.AuthConstants;
import vn.com.easyjob.util.SortUtils;

@Tag(name = "apply")
@RestController
@RequestMapping("/api/v1/apply-job")
@SecurityRequirement(name = "bearer-key")
public class ApplyV1Controller {

    @Autowired
    AppliedJobService appliedJobService;

    @PreAuthorize(AuthConstants.APPLIER)
    @PostMapping("/{id}")
    public ResponseEntity<?> applyV1(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ResponseDTO("Aplly Job Successfully",appliedJobService.addAppliedJob(id))
        );
    }

    @PreAuthorize(AuthConstants.EMPLOYER)
    @GetMapping("/{id}/{applieStatusEnum}")
    public ResponseEntity<?> getAppliedJobs(Pageable pageable,
                                            @PathVariable Long id ,
                                            @PathVariable ApplieStatusEnum applieStatusEnum) {
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
        return ResponseEntity.ok().body(
                new ResponseDTO(HttpStatus.ACCEPTED.name(),appliedJobService.getAppliedJobs(id,applieStatusEnum,pageable))
        );
    }

    @PreAuthorize(AuthConstants.EMPLOYER)
    @PostMapping
    public ResponseEntity<?> AcceptApplyV1(@RequestParam Long apllyid, @RequestParam Long profileId) {
        return ResponseEntity.ok().body(apllyid+ "-" + profileId);
    }
}
