package vn.com.easyjob.specification;

import org.springframework.data.jpa.domain.Specification;
import vn.com.easyjob.model.entity.Profile;

public class ProfileSpecification {
    public static Specification<Profile> hasKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            String likeKeyword = "%" + keyword.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("fullname")), likeKeyword),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("account").get("email")), likeKeyword)
            );
        };
    }

    public static Specification<Profile> hasIsDeleted(Boolean isDeleted) {
        return (root, query, criteriaBuilder) ->
                isDeleted == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("isDeleted"), isDeleted);
    }

    public static Specification<Profile> hasRole(Integer role) {
        return (root, query, criteriaBuilder) -> {
          
            if (role == null || role != 2 && role != 3) {
                return criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("account").get("role").get("id"), 2),
                        criteriaBuilder.equal(root.get("account").get("role").get("id"), 3)
                );
            }
            return criteriaBuilder.equal(root.get("account").get("role").get("id"), role);
        };
    }
}
