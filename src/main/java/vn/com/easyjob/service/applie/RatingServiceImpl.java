package vn.com.easyjob.service.applie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.Rating;
import vn.com.easyjob.repository.RatingRepository;

@Service
public class RatingServiceImpl extends BaseService<Rating, Long> implements RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Override
    protected IRepository<Rating, Long> getRepository() {
        return ratingRepository;
    }
}
