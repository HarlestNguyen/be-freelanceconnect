package vn.com.easyjob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.easyjob.model.dto.ResponseDTO;
import vn.com.easyjob.model.record.JobDetailRecord;
import vn.com.easyjob.service.job.JobDetailService;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    @Autowired
    private JobDetailService jobDetailService;

    // API lấy tất cả các công việc (có phân trang)
    @GetMapping
    public ResponseEntity<?> getAllJobs(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(jobDetailService.findAllJobs(pageable)));
    }

    @PostMapping
    public ResponseEntity<?> createJob(@ModelAttribute JobDetailRequest request) throws Exception {
        jobService.createJob(request);  // Không cần kết quả trả về
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Job created successfully"));
    }

    // API lấy công việc theo ID
    @GetMapping("/{jobId}")
    public ResponseEntity<?> findJobById(@PathVariable("jobId") Long jobId) {
       return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(jobService.findJobById(jobId)));
    }
}
