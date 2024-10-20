package vn.com.easyjob.repository.custom;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.com.easyjob.base.CustomPageResponse;



public interface CustomPagingRepository<T> {
    CustomPageResponse<T> findCustomPage(Pageable pageable, Class<T> entityClass);
    CustomPageResponse<T> findCustomPage(Pageable pageable, Specification<T> specification, Class<T> entityClass);
}
