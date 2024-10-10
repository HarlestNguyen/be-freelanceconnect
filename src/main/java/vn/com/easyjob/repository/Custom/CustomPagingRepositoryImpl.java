package vn.com.easyjob.repository.Custom;


import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import vn.com.easyjob.base.CustomPageResponse;

import java.util.List;


@Component
public class CustomPagingRepositoryImpl<T> implements CustomPagingRepository<T> {
    @Autowired
    private EntityManager entityManager;

    @Override
    public CustomPageResponse<T> findCustomPage(Pageable pageable,Class<T> entityClass) {
        // Viết query phù hợp cho entity của bạn
        TypedQuery<T> query = entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass);

        // Áp dụng phân trang
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<T> content = query.getResultList();

        // Lấy tổng số lượng phần tử
        long totalElements = entityManager.createQuery("SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e", Long.class)
                .getSingleResult();

        // Tính toán số trang
        int totalPages = pageable.getPageSize() == 0 ? 1 : (int) Math.ceil((double) totalElements / pageable.getPageSize());

        // Số phần tử trong trang hiện tại
        int numberOfElements = content.size();

        // Kiểm tra xem có trang trước và trang sau hay không
        boolean first = pageable.getPageNumber() == 0;
        boolean last = pageable.getPageNumber() == totalPages - 1;
        boolean empty = content.isEmpty();

        // Trả về đối tượng CustomPageResponse
        return new CustomPageResponse<>(totalElements, totalPages, pageable.getPageSize(), pageable.getPageNumber(),
                content, first, last, numberOfElements, empty);
    }
}
