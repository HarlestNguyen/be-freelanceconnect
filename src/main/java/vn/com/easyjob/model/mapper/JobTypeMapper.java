package vn.com.easyjob.model.mapper;

import org.mapstruct.Mapper;
import vn.com.easyjob.base.BaseMapper;
import vn.com.easyjob.model.dto.JobTypeDTO;
import vn.com.easyjob.model.entity.JobType;

@Mapper(componentModel = "spring")
public interface JobTypeMapper extends BaseMapper<JobType, JobTypeDTO> {
}
