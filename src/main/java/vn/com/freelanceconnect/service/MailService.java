package vn.com.freelanceconnect.service;

import org.springframework.stereotype.Service;
import vn.com.freelanceconnect.util.EmailSubjectEnum;
import vn.com.freelanceconnect.util.TypeMailEnum;

@Service
public interface MailService {
    Boolean sendWithTemplate(String email, String content, EmailSubjectEnum subject, TypeMailEnum type);
}
