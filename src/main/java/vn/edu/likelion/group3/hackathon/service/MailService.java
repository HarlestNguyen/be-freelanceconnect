package vn.edu.likelion.group3.hackathon.service;

import vn.edu.likelion.group3.hackathon.util.EmailSubjectEnum;
import vn.edu.likelion.group3.hackathon.util.TypeMailEnum;
import org.springframework.stereotype.Service;

@Service
public interface MailService {
    Boolean sendWithTemplate(String email, String content, EmailSubjectEnum subject, TypeMailEnum type);
}
