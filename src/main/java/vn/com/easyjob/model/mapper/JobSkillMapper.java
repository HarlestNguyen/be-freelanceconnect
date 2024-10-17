package vn.com.easyjob.model.mapper;

import org.mapstruct.Mapper;
import vn.com.easyjob.base.BaseMapper;
import vn.com.easyjob.model.dto.JobSkillDTO;
import vn.com.easyjob.model.entity.JobSkill;

@Mapper(componentModel = "spring")
public interface JobSkillMapper extends BaseMapper<JobSkill, JobSkillDTO> {
}
