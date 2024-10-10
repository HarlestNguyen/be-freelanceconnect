package vn.com.easyjob.repository.Custom;

import org.springframework.data.domain.Pageable;
import vn.com.easyjob.base.CustomPageResponse;

@FunctionalInterface
public interface CustomPagingRepository<T> {
    CustomPageResponse<T> findCustomPage(Pageable pageable, Class<T> entityClass);

}
