package vn.com.freelanceconnect.service;

import vn.com.freelanceconnect.exception.ErrorHandler;
import vn.com.freelanceconnect.generic.IRepository;
import vn.com.freelanceconnect.generic.IService;
import vn.com.freelanceconnect.entity.EntityDefine;
import org.springframework.http.HttpStatus;

import java.util.Collection;

public abstract class AbstractService<T extends EntityDefine, ID> implements IService<T, ID> {

    protected abstract IRepository<T, ID> getRepository();

    public T save(T t) {
        try {
            return getRepository().save(t);
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public Boolean delete(ID id) {
        try {
            T t = findOne(id);
            t.setIsDeleted(true);
            return save(t).getIsDeleted();
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Collection<T> findAll() {
        try {
            return getRepository().findAllByIsDeletedFalse();
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public T findOne(ID id) {
        return getRepository().findById(id)
                .filter(t -> !t.getIsDeleted())
                .orElseThrow(() -> new ErrorHandler(HttpStatus.NOT_FOUND, "Entity not found or had bean deleted."));
    }
}
