package vn.com.easyjob.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.com.easyjob.model.dto.ResponseDTO;
import vn.com.easyjob.model.record.JobDetailRecord;
import vn.com.easyjob.service.job.JobDetailService;
import vn.com.easyjob.util.AuthConstants;
import vn.com.easyjob.util.JobApprovalStatusEnum;

import java.util.Map;

@Tag(name = "job")
@RestController
@RequestMapping("/api/v1/job")
public class JobV1Controller {
    @Autowired
    private JobDetailService jobDetailService;

    // API lấy tất cả các công việc (có phân trang)
    @GetMapping
    @Parameter(in = ParameterIn.QUERY, name = "page", schema = @Schema(type = "integer", defaultValue = "0"))
    @Parameter(in = ParameterIn.QUERY, name = "size", schema = @Schema(type = "integer", defaultValue = "12"))
    @Parameter(in = ParameterIn.QUERY, name = "sortBy", schema = @Schema(type = "string", defaultValue = "id"))
    @Parameter(in = ParameterIn.QUERY, name = "direction", schema = @Schema(type = "string", defaultValue = "asc", allowableValues = {"asc", "desc"}))
    @Parameter(in = ParameterIn.QUERY, name = "title", schema = @Schema(type = "string"), description = "Job title for filtering")
    @Parameter(in = ParameterIn.QUERY, name = "districtId", schema = @Schema(type = "integer"), description = "District ID for filtering")
    @Parameter(in = ParameterIn.QUERY, name = "provinceId", schema = @Schema(type = "integer"), description = "Province ID for filtering")
    @Parameter(in = ParameterIn.QUERY, name = "startDate", schema = @Schema(type = "string", format = "date"), description = "Start date in yyyy-MM-dd format")
    @Parameter(in = ParameterIn.QUERY, name = "endDate", schema = @Schema(type = "string", format = "date"), description = "End date in yyyy-MM-dd format")
    @Parameter(in = ParameterIn.QUERY, name = "jobTypeId", schema = @Schema(type = "integer"), description = "Job type ID for filtering")
    @Parameter(in = ParameterIn.QUERY, name = "jobSkillId", schema = @Schema(type = "integer"), description = "Job skill ID for filtering")
    @Parameter(in = ParameterIn.QUERY, name = "approval", schema = @Schema(type = "string", defaultValue = "", allowableValues = {"PENDING", "REJECTED", "APPROVED"}), description = "Required role ROLE_ADMIN")
    public ResponseEntity<?> getAllJobs(@RequestParam(required = false) Map<String, String> params) {
        if (params.containsKey("approval")) {
            // Lấy thông tin Authentication hiện tại
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Kiểm tra xem người dùng có phải là ROLE_ADMIN không
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            // Nếu không có quyền ROLE_ADMIN, ném ngoại lệ AccessDenied
            if (!isAdmin) {
                throw new AccessDeniedException("You do not have permission to access this resource.");
            }
        }

        // Nếu không có "adminParam" hoặc người dùng có ROLE_ADMIN thì tiếp tục xử lý
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(jobDetailService.findAllJobs(params)));
    }

    @PreAuthorize(AuthConstants.EMPLOYER)
    @SecurityRequirement(name = "bearer-key")
    @PostMapping
    public ResponseEntity<?> createJob(@ModelAttribute JobDetailRecord request) throws Exception {
        jobDetailService.createJob(request);  // Không cần kết quả trả về
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Job created successfully"));
    }

    // API lấy công việc theo ID
    @GetMapping("/{jobId}")
    public ResponseEntity<?> findJobById(@PathVariable("jobId") Long jobId) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(jobDetailService.findJobById(jobId)));
    }


    @PostMapping("toggle-accept/{jobId}")
    public ResponseEntity<?> toggleaccept(@PathVariable("jobId") Long jobId) {
            jobDetailService.toggleAcceptJob(jobId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Job accepted"));
    }


    @PostMapping("toggle-reject/{jobId}")
    public ResponseEntity<?> togglereject(@PathVariable("jobId") Long jobId) {
        jobDetailService.toggleRejectJob(jobId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Job rejected"));
    }


    @PreAuthorize(AuthConstants.APPLIER)
    @SecurityRequirement(name = "bearer-key")
    @PostMapping("/{id}/applie")
    public ResponseEntity<?> applyJob(@PathVariable("id") Long id) {
        return null;
    }
}
