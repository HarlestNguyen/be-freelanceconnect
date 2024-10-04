package vn.com.freelanceconnect.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;

@NoRepositoryBean
public interface IRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID> {
    Collection<T> findAllByIsDeletedFalse();
}
