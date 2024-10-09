package vn.com.easyjob.repository;

import org.springframework.stereotype.Repository;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.Rating;

@Repository
public interface RatingRepository extends IRepository<Rating, Long> {
}
