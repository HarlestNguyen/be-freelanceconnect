package vn.com.easyjob.service.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseAbstractService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.exception.ErrorHandler;
import vn.com.easyjob.model.dto.JobTypeDTO;
import vn.com.easyjob.model.entity.JobType;
import vn.com.easyjob.model.record.JobtypeRecord;
import vn.com.easyjob.repository.JobTypeRepository;

@Service
public class JobTypeServiceImpl extends BaseAbstractService<JobType, Long> implements JobTypeService {
    @Autowired
    private JobTypeRepository jobTypeRepository;

    @Override
    protected IRepository<JobType, Long> getRepository() {
        return jobTypeRepository;
    }

    @Override
    public JobTypeDTO updateJobType(JobTypeDTO jobtypeDTO) {
        JobType jobType = jobTypeRepository.findById(jobtypeDTO.getId()).orElseThrow(
                () -> new ErrorHandler(HttpStatus.NOT_FOUND,"JobType Updated Not Found")
        );
        jobType.setName(jobtypeDTO.getName());
        jobType.setDescription(jobtypeDTO.getDescription());
        jobType.setMaxPrice(jobtypeDTO.getMaxPrice());
        jobType.setMinPrice(jobtypeDTO.getMinPrice());
        return convertToDTO(jobTypeRepository.save(jobType));
    }


    private JobTypeDTO convertToDTO(JobType jobType) {
        return JobTypeDTO.builder()
                .id(jobType.getId())
                .name(jobType.getName())
                .description(jobType.getDescription())
                .maxPrice(jobType.getMaxPrice())
                .minPrice(jobType.getMinPrice())
                .build();
    }

    @Override
    public JobTypeDTO save(JobtypeRecord jobTypeRecord) {
        // Step 1: Convert JobtypeRecord to JobType entity and save
        JobType jobType = JobType.builder()
                .name(jobTypeRecord.name())
                .description(jobTypeRecord.description())
                .minPrice(jobTypeRecord.minPrice())
                .maxPrice(jobTypeRecord.maxPrice())
                .build();

        // Step 2: Save entity to the repository
        JobType savedJobType = jobTypeRepository.save(jobType);

        // Step 3: Return the saved entity as a DTO
        return JobTypeDTO.builder()
                .id(savedJobType.getId())
                .name(savedJobType.getName())
                .description(savedJobType.getDescription())
                .minPrice(savedJobType.getMinPrice())
                .maxPrice(savedJobType.getMaxPrice())
                .build();
    }
}
