package vn.com.easyjob.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.easyjob.base.BaseController;
import vn.com.easyjob.base.BaseControllerDTO;
import vn.com.easyjob.base.BaseServiceDTO;
import vn.com.easyjob.base.IService;
import vn.com.easyjob.model.dto.JobTypeDTO;
import vn.com.easyjob.model.entity.JobType;
import vn.com.easyjob.service.job.JobTypeService;
import vn.com.easyjob.util.AuthConstants;

@Tag(name = "job-type-v1")
@RestController
@RequestMapping("/api/v1/job-type")
public class JobTypeV1Controller extends BaseControllerDTO<JobType, JobTypeDTO, Long> {
    @Autowired
    private JobTypeService jobTypeService;


    @Override
    public BaseServiceDTO<JobType, JobTypeDTO, Long> getService() {
        return (BaseServiceDTO<JobType, JobTypeDTO, Long>) jobTypeService;
    }

    @Override
    @PreAuthorize(AuthConstants.ADMIN)
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> delete(Long id) {
        return super.delete(id);
    }


}
