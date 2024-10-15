package vn.com.easyjob.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.easyjob.base.BaseController;
import vn.com.easyjob.base.BaseControllerDTO;
import vn.com.easyjob.base.BaseServiceDTO;
import vn.com.easyjob.base.IService;
import vn.com.easyjob.model.dto.JobSkillDTO;
import vn.com.easyjob.model.dto.ResponseDTO;
import vn.com.easyjob.model.entity.JobSkill;
import vn.com.easyjob.model.mapper.JobSkillMapper;
import vn.com.easyjob.model.record.JobSkillRecord;
import vn.com.easyjob.response.ResponseObject;
import vn.com.easyjob.service.job.JobSkillService;
import vn.com.easyjob.util.AuthConstants;

import java.util.Date;
import java.util.List;

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

    // ================================================
    //               	CREATE JOB SKILL
    // ================================================
    @PostMapping
    public ResponseEntity<?> save(@RequestBody JobSkillRecord jobSkillRecord){
        JobSkillDTO jobSkillDTO = jobSkillService.save(jobSkillRecord);

        if (jobSkillDTO != null) {
            ResponseObject<JobSkillDTO> response = new ResponseObject<>(
                    "Thành công !",
                    HttpStatus.CREATED.value(),
                    jobSkillDTO,
                    new Date()
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to add job skill", HttpStatus.BAD_REQUEST);
        }
    }

    // ================================================
    //               	UPDATE JOB SKILL
    // ================================================
    @PutMapping
    public ResponseEntity<?> update(@RequestBody JobSkillDTO jobSkillDTO) {
        JobSkillDTO jobSkillDto = jobSkillService.updateJobSkill(jobSkillDTO);

        if (jobSkillDto != null) {
            ResponseObject<JobSkillDTO> response = new ResponseObject<>(
                    "Update success!",
                    HttpStatus.OK.value(),
                    jobSkillDto,
                    new Date()
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseObject<String> response = new ResponseObject<>(
                    "Failed to update Course ID:" + jobSkillDto.getId(),
                    HttpStatus.NOT_FOUND.value(),
                    null,
                    new Date()
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



}
