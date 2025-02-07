package vn.com.easyjob.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.com.easyjob.model.dto.ResponseDTO;
import vn.com.easyjob.model.entity.JobDetail;
import vn.com.easyjob.model.entity.Profile;
import vn.com.easyjob.model.record.JobDetailRecord;
import vn.com.easyjob.service.auth.ProfileServiceImpl;
import vn.com.easyjob.service.job.JobDetailService;
import vn.com.easyjob.util.*;

import java.util.Map;

@Tag(name = "job")
@RestController
@RequestMapping("/api/v1/job")
public class JobV1Controller {
    private static final Logger log = LoggerFactory.getLogger(JobV1Controller.class);
    @Autowired
    private JobDetailService jobDetailService;
    @Autowired
    private ProfileServiceImpl profileServiceImpl;

    // API lấy tất cả các công việc (có phân trang)
    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    @Parameter(in = ParameterIn.QUERY, name = "page", schema = @Schema(type = "integer"))
    @Parameter(in = ParameterIn.QUERY, name = "size", schema = @Schema(type = "integer"))
    @Parameter(in = ParameterIn.QUERY, name = "sortBy", schema = @Schema(type = "string", allowableValues = { "title",  "startDate",  "endDate",  "duration",  "jobType"}))
    @Parameter(in = ParameterIn.QUERY, name = "direction", schema = @Schema(type = "string", allowableValues = {"asc", "desc"}))
    @Parameter(in = ParameterIn.QUERY, name = "title", schema = @Schema(type = "string"), description = "Job title for filtering")
    @Parameter(in = ParameterIn.QUERY, name = "districtId", schema = @Schema(type = "integer"), description = "District ID for filtering")
    @Parameter(in = ParameterIn.QUERY, name = "provinceId", schema = @Schema(type = "integer"), description = "Province ID for filtering")
    @Parameter(in = ParameterIn.QUERY, name = "startDate", schema = @Schema(type = "string", format = "date"), description = "Start date in yyyy-MM-dd format")
    @Parameter(in = ParameterIn.QUERY, name = "endDate", schema = @Schema(type = "string", format = "date"), description = "End date in yyyy-MM-dd format")
    @Parameter(in = ParameterIn.QUERY, name = "jobTypeId", schema = @Schema(type = "integer"), description = "Job type ID for filtering")
    @Parameter(in = ParameterIn.QUERY, name = "jobSkillId", schema = @Schema(type = "integer"), description = "Job skill ID for filtering")
    @Parameter(in = ParameterIn.QUERY, name = "approval", schema = @Schema(type = "string", allowableValues = {"ALL", "PENDING", "REJECTED", "APPROVED"}), description = "Required role ROLE_ADMIN")
    @Parameter(in = ParameterIn.QUERY, name = "owner", schema = @Schema(type = "string", allowableValues = {"self"}), description = "Required role ROLE_EMPLOYER")
    public ResponseEntity<?> getAllJobs(@RequestParam(required = false) Map<String, String> params) {
        // Nếu không có "adminParam" hoặc người dùng có ROLE_ADMIN thì tiếp tục xử lý
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(jobDetailService.findAllJobs(params)));
    }

    @PreAuthorize(AuthConstants.EMPLOYER)
    @SecurityRequirement(name = "bearer-key")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createJob(@ModelAttribute JobDetailRecord request) throws Exception {
        jobDetailService.createJob(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Job created successfully"));
    }

    // API lấy công việc theo ID
    @GetMapping("/{jobId}")
    public ResponseEntity<?> findJobById(@PathVariable("jobId") Long jobId) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(jobDetailService.findJobById(jobId)));
    }

    @GetMapping("/job-my-self")
    @PreAuthorize(AuthConstants.EMPLOYER)
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> findMyJobsByApproval(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction", schema = @Schema(allowableValues = {"ASC", "DESC"}))
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "jobApprovalStatusEnum", defaultValue = "APPROVED") JobApprovalStatusEnum jobApprovalStatusEnum
    ) {
        // Tạo đối tượng Sort
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        // Kiểm tra tính hợp lệ của trường sortBy, nếu không hợp lệ đặt sort mặc định là "id"
        if (!SortUtils.isValidSortField(JobDetail.class, sortBy)) {
            sort = Sort.by(Sort.Direction.fromString(direction),"id"); // Đặt sort mặc định là "id"
        }
        // Tạo đối tượng Pageable với Sort đã xử lý
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("get My Job Successfully",
                jobDetailService.findJobApprovalBySelf(pageable, jobApprovalStatusEnum)
        ));
    }

    @GetMapping("/job-my-self-by-applierstatus")
    @PreAuthorize(AuthConstants.EMPLOYER)
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> findMyJobsbyApplierStatus(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction", schema = @Schema(allowableValues = {"ASC", "DESC"}))
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "ApplieStatusEnum", defaultValue = "WAITING") ApplieStatusEnum applieStatusEnum
    ) {
        // Tạo đối tượng Sort
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        // Kiểm tra tính hợp lệ của trường sortBy, nếu không hợp lệ đặt sort mặc định là "id"
        if (!SortUtils.isValidSortField(JobDetail.class, sortBy)) {
            sort = Sort.by(Sort.Direction.fromString(direction),"id"); // Đặt sort mặc định là "id"
        }
        // Tạo đối tượng Pageable với Sort đã xử lý
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("get My Job Successfully",
//                jobDetailService.findJobApprovalBySelf(pageable, jobApprovalStatusEnum)
                null
        ));

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


//    @PreAuthorize(AuthConstants.APPLIER)
//    @SecurityRequirement(name = "bearer-key")
//    @PostMapping("/{id}")
//    public ResponseEntity<?> applyJob(@PathVariable("id") Long id, @RequestParam JobActionEnum action) {
//
//        return null;
//    }
}
