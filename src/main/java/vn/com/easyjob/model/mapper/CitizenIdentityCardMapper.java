package vn.com.easyjob.model.mapper;

import org.mapstruct.Mapper;
import vn.com.easyjob.base.BaseMapper;
import vn.com.easyjob.model.dto.CitizenIdentityCardDTO;
import vn.com.easyjob.model.entity.CitizenIdentityCard;

@Mapper(componentModel = "spring")
public interface CitizenIdentityCardMapper extends BaseMapper<CitizenIdentityCard, CitizenIdentityCardDTO> {
}
