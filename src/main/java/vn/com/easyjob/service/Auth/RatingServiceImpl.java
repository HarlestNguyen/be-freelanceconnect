package vn.com.easyjob.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseAbstractService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.Rating;
import vn.com.easyjob.repository.RatingRepository;
import vn.com.easyjob.service.RatingService;

@Service
public class RatingServiceImpl extends BaseAbstractService<Rating, Long> implements RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Override
    protected IRepository<Rating, Long> getRepository() {
        return ratingRepository;
    }
}
