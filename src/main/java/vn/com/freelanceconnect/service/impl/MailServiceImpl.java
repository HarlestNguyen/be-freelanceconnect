package vn.com.freelanceconnect.service.impl;

import vn.com.freelanceconnect.util.EmailSubjectEnum;
import vn.com.freelanceconnect.exception.ErrorHandler;
import vn.com.freelanceconnect.service.MailService;
import vn.com.freelanceconnect.util.TypeMailEnum;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public Boolean sendWithTemplate(String email, String content, EmailSubjectEnum subject, TypeMailEnum type) {
        try {
            String htmlContent = loadHtmlTemplate(type.getTemplate());
            switch (type) {
                case OTP -> {
                    htmlContent = htmlContent.replaceAll("##otp##", content);
                    sendHtmlEmail(email, subject.getSubject(), htmlContent);

                }
                case VERIFY_LINK -> {
                    htmlContent = htmlContent.replaceAll("##verify_link##", content);
                    sendHtmlEmail(email, subject.getSubject(), htmlContent);
                }
            }
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return true;
    }

    private void sendHtmlEmail(String toMail, String subject, String htmlBody) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("", "Third Team");
        helper.setTo(toMail);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        javaMailSender.send(message);
    }

    private String loadHtmlTemplate(String templateName) {
        try (InputStream inputStream = new ClassPathResource(templateName).getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
