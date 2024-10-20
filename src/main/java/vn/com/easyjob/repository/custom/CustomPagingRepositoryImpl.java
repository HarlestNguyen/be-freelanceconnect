package vn.com.easyjob.repository.custom;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vn.com.easyjob.base.CustomPageResponse;


import java.util.ArrayList;
import java.util.List;


@Component
public class CustomPagingRepositoryImpl<T> implements CustomPagingRepository<T> {
    @Autowired
    private EntityManager entityManager;

    @Override
    public CustomPageResponse<T> findCustomPage(Pageable pageable, Class<T> entityClass) {
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

    @Override
    public CustomPageResponse<T> findCustomPage(Pageable pageable, Specification<T> specification, Class<T> entityClass) {
        // Tạo CriteriaBuilder và CriteriaQuery
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);

        // Áp dụng Specification (điều kiện)
        if (specification != null) {
            Predicate predicate = specification.toPredicate(root, cq, cb);
            cq.where(predicate);
        }

        // Thêm ORDER BY theo Pageable (phân trang)
        if (pageable.getSort().isSorted()) {
            List<Order> orders = new ArrayList<>();
            pageable.getSort().forEach(sortOrder -> {
                if (sortOrder.getDirection() == Sort.Direction.ASC) {
                    orders.add(cb.asc(root.get(sortOrder.getProperty())));
                } else {
                    orders.add(cb.desc(root.get(sortOrder.getProperty())));
                }
            });
            cq.orderBy(orders);
        }

        // Tạo truy vấn
        TypedQuery<T> query = entityManager.createQuery(cq);

        // Áp dụng phân trang
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        // Lấy nội dung kết quả
        List<T> content = query.getResultList();

        // Lấy tổng số lượng phần tử dựa trên điều kiện Specification
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(entityClass);
        countQuery.select(cb.count(countRoot));

        // Áp dụng Specification cho truy vấn đếm
        if (specification != null) {
            Predicate countPredicate = specification.toPredicate(countRoot, countQuery, cb);
            countQuery.where(countPredicate);
        }
        long totalElements = entityManager.createQuery(countQuery).getSingleResult();

        // Tính toán số trang
        int totalPages = pageable.getPageSize() == 0 ? 1 : (int) Math.ceil((double) totalElements / pageable.getPageSize());

        // Số phần tử trong trang hiện tại
        int numberOfElements = content.size();

        // Kiểm tra trang đầu và trang cuối
        boolean first = pageable.getPageNumber() == 0;
        boolean last = pageable.getPageNumber() == totalPages - 1;
        boolean empty = content.isEmpty();

        // Trả về đối tượng CustomPageResponse
        return new CustomPageResponse<>(totalElements, totalPages, pageable.getPageSize(), pageable.getPageNumber(),
                content, first, last, numberOfElements, empty);
    }



}
