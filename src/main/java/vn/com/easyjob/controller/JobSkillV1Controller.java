package vn.com.easyjob.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.easyjob.base.BaseControllerDTO;
import vn.com.easyjob.base.BaseServiceDTO;
import vn.com.easyjob.model.dto.JobSkillDTO;
import vn.com.easyjob.model.dto.ResponseDTO;
import vn.com.easyjob.model.entity.JobSkill;
import vn.com.easyjob.model.record.JobSkillRecord;
import vn.com.easyjob.service.job.JobSkillService;
import vn.com.easyjob.util.AuthConstants;

@Tag(name = "job-skill")
@RestController
@RequestMapping("/api/v1/job-skill")
public class JobSkillV1Controller extends BaseControllerDTO<JobSkill, JobSkillDTO, Long> {
    @Autowired
    private JobSkillService jobSkillService;


    @Override
    public BaseServiceDTO<JobSkill, JobSkillDTO, Long> getService() {
        return (BaseServiceDTO<JobSkill, JobSkillDTO, Long>) jobSkillService;
    }

    @Override
    @PreAuthorize(AuthConstants.ADMIN)
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> delete(Long id) {
        return super.delete(id);
    }

     @PreAuthorize(AuthConstants.ADMIN)
     @SecurityRequirement(name = "bearer-key")
     @PostMapping
     public ResponseEntity<?> save(@RequestBody JobSkillRecord record) {
         return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Create Job Skill successfully", jobSkillService.save(record)));
     }

     @PreAuthorize(AuthConstants.ADMIN)
     @SecurityRequirement(name = "bearer-key")
     @PatchMapping("/{id}")
     public ResponseEntity<?> update(@RequestBody JobSkillRecord record, @PathVariable Long id) {
         return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Update Job Skill successfully", jobSkillService.update(id, record)));
     }
}
