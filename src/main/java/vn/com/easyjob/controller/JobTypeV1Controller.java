package vn.com.easyjob.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.easyjob.base.BaseController;
import vn.com.easyjob.base.IService;
import vn.com.easyjob.model.entity.JobType;
import vn.com.easyjob.service.JobTypeService;

@Tag(name = "job-type-v1")
@RestController
@RequestMapping("/api/v1/job-type")
public class JobTypeV1Controller extends BaseController<JobType, Long> {
    @Autowired
    private JobTypeService jobTypeService;


    @Override
    public IService<JobType, Long> getService() {
        return jobTypeService;
    }
}
