package vn.com.easyjob.base;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public interface IService<T, ID> {

    T save(T t);

    Boolean delete(ID id);

    Collection<T> findAll();

    T findOne(ID id);

}
