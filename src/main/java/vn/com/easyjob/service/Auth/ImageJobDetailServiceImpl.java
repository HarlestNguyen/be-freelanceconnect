package vn.com.easyjob.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.easyjob.base.BaseAbstractService;
import vn.com.easyjob.base.IRepository;
import vn.com.easyjob.model.entity.ImageJobDetail;
import vn.com.easyjob.repository.ImageJobDetailRepository;
import vn.com.easyjob.service.ImageJobDetailService;

@Service
public class ImageJobDetailServiceImpl extends BaseAbstractService<ImageJobDetail, Long> implements ImageJobDetailService {
    @Autowired
    private ImageJobDetailRepository imageJobDetailRepository;

    @Override
    protected IRepository<ImageJobDetail, Long> getRepository() {
        return imageJobDetailRepository;
    }
}
