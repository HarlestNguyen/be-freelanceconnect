package vn.com.easyjob.service.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseMapper;
import vn.com.easyjob.base.BaseService;
import vn.com.easyjob.base.BaseServiceDTO;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.exception.ErrorHandler;
import vn.com.easyjob.model.dto.JobSkillDTO;
import vn.com.easyjob.model.dto.JobTypeDTO;
import vn.com.easyjob.model.entity.JobSkill;
import vn.com.easyjob.model.entity.JobType;
import vn.com.easyjob.model.mapper.JobSkillMapper;
import vn.com.easyjob.model.record.JobSkillRecord;
import vn.com.easyjob.repository.JobSkillRepository;

@Service
public class JobSkillServiceImpl extends BaseServiceDTO<JobSkill, JobSkillDTO, Long> implements JobSkillService {
    @Autowired
    private JobSkillRepository jobSkillRepository;

    @Autowired
    private JobSkillMapper jobSkillMapper;

    @Override
    protected IRepository<JobSkill, Long> getRepository() {return jobSkillRepository;}


    @Override
    public JobSkillDTO updateJobSkill(JobSkillDTO jobSkillDTO) {
        JobSkill jobSkill = jobSkillRepository.findById(jobSkillDTO.getId()).orElseThrow(
                () -> new ErrorHandler(HttpStatus.NOT_FOUND, "JobSkill id Updated Not Found")
        );
        jobSkill.setSkill(jobSkillDTO.getSkill());
        jobSkill.setJobDetails(jobSkill.getJobDetails());

        return convertToDTO(jobSkillRepository.save(jobSkill));
    }

    private JobSkillDTO convertToDTO(JobSkill jobSkill) {
        return JobSkillDTO.builder()
                .id(jobSkill.getId())
                .skill(jobSkill.getSkill())
                .description(jobSkill.getDescription())
                .build();
    }

    @Override
    public JobSkillDTO save(JobSkillRecord jobSkillRecord){
        // Step 1: Convert JobSkillRecord to JobType entity and save
        JobSkill jobSkill = JobSkill.builder()
                .skill(jobSkillRecord.skill())
                .description(jobSkillRecord.description())
                .build();

        // Step 2: Save entity to the repository
        JobSkill saveJobSkill = jobSkillRepository.save(jobSkill);

        // Step 3: Return the saved entity as a DTO
        return JobSkillDTO.builder()
                .id(saveJobSkill.getId())
                .skill(saveJobSkill.getSkill())
                .description(saveJobSkill.getDescription())
                .build();
    }


    @Override
    protected BaseMapper<JobSkill, JobSkillDTO> getMapper() {
        return jobSkillMapper;
    }

}
