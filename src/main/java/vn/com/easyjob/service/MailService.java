package vn.com.easyjob.service;

import org.springframework.stereotype.Service;
import vn.com.easyjob.util.EmailSubjectEnum;
import vn.com.easyjob.util.TypeMailEnum;

@Service
public interface MailService {
    Boolean sendWithTemplate(String email, String content, EmailSubjectEnum subject, TypeMailEnum type);
}
