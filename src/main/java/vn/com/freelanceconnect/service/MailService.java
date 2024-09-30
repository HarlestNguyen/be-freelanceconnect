package vn.com.freelanceconnect.service;

import vn.com.freelanceconnect.util.EmailSubjectEnum;
import vn.com.freelanceconnect.util.TypeMailEnum;
import org.springframework.stereotype.Service;

@Service
public interface MailService {
    Boolean sendWithTemplate(String email, String content, EmailSubjectEnum subject, TypeMailEnum type);
}
