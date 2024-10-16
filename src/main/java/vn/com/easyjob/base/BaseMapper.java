package vn.com.easyjob.base;

public interface BaseMapper<ENTITY, DTO> {
    DTO toDto(ENTITY entity);

    ENTITY toEntity(DTO dto);
}
