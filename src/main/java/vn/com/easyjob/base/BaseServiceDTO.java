package vn.com.easyjob.base;

import java.util.Collection;

public abstract class BaseServiceDTO<T extends BaseEntity, DTO, ID> extends BaseService<T, ID> implements IService<T, ID> {

    protected abstract BaseMapper<T, DTO> getMapper();

    public DTO saveToDTO(T t) {
        return getMapper().toDto(super.save(t));
    }

    public Collection<DTO> findAllToDTO() {
        return super.findAll().stream().map(t -> getMapper().toDto(t)).toList();
    }

    public DTO findOneToDTO(ID id) {
        return getMapper().toDto(super.findOne(id));
    }
}
