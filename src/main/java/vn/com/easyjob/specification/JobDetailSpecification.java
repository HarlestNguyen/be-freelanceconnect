package vn.com.easyjob.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vn.com.easyjob.model.entity.JobApprovalStatus;
import vn.com.easyjob.model.entity.JobDetail;
import vn.com.easyjob.model.entity.JobSkill;
import vn.com.easyjob.model.entity.JobType;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class JobDetailSpecification {

    public Specification<JobDetail> hasTitle(String title) {
        return (root, query, cb) ->
                (title == null || title.trim().isEmpty()) ? cb.conjunction() : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public Specification<JobDetail> hasDistrictId(Integer districtId) {
        return (root, query, cb) ->
                districtId == null ? cb.conjunction() : cb.equal(root.get("districtId"), districtId);
    }

    public Specification<JobDetail> hasProvinceId(Integer provinceId) {
        return (root, query, cb) ->
                provinceId == null ? cb.conjunction() : cb.equal(root.get("provinceId"), provinceId);
    }

    public Specification<JobDetail> isWithinDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            Predicate startPredicate = (startDate == null) ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("startDate"), startDate);
            Predicate endPredicate = (endDate == null) ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("endDate"), endDate);
            return cb.and(startPredicate, endPredicate);  // Cả hai điều kiện sẽ được kết hợp
        };
    }

    public Specification<JobDetail> hasJobType(JobType jobType) {
        return (root, query, cb) ->
                jobType == null ? cb.conjunction() : cb.equal(root.get("jobType"), jobType);
    }

    public Specification<JobDetail> hasApprovalStatus(JobApprovalStatus jobApprovalStatus) {
        return (root, query, cb) ->
                jobApprovalStatus == null ? cb.conjunction() : cb.equal(root.get("jobApprovalStatus"), jobApprovalStatus);
    }

    public Specification<JobDetail> hasJobSkills(Set<JobSkill> skills) {
        return (root, query, cb) -> {
            if (skills == null || skills.isEmpty()) {
                return cb.conjunction();  // Nếu không có kỹ năng nào, bỏ qua điều kiện này
            }
            // Tạo một điều kiện AND cho từng kỹ năng
            Predicate[] predicates = skills.stream()
                    .map(skill -> cb.isMember(skill, root.get("jobSkills")))  // Kiểm tra kỹ năng có trong tập jobSkills
                    .toArray(Predicate[]::new);

            return cb.and(predicates);  // Trả về điều kiện AND cho tất cả kỹ năng
        };
    }

    public Specification<JobDetail> hasJobSkill(JobSkill skill) {
        return (root, query, cb) -> {
            if (skill == null) {
                return cb.conjunction();  // Nếu không có kỹ năng nào, bỏ qua điều kiện này
            }

            // Kiểm tra kỹ năng có trong tập jobSkills của JobDetail
            return cb.isMember(skill, root.get("jobSkills"));
        };
    }

}

