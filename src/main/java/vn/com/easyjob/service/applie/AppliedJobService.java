package vn.com.easyjob.service.applie;

import org.springframework.data.domain.Pageable;
import vn.com.easyjob.base.CustomPageResponse;
import vn.com.easyjob.base.IService;
import vn.com.easyjob.model.dto.AppliesDTO;
import vn.com.easyjob.model.dto.JobDTO;
import vn.com.easyjob.model.dto.ProfileAppliesDTO;
import vn.com.easyjob.model.entity.AppliedJob;
import vn.com.easyjob.model.entity.Profile;
import vn.com.easyjob.util.ApplieStatusEnum;

public interface AppliedJobService extends IService<AppliedJob, Long> {
    AppliesDTO addAppliedJob(Long jobId);
    CustomPageResponse<ProfileAppliesDTO> getAppliedJobs(Long jobId, ApplieStatusEnum applieStatus, Pageable pageable);
}
