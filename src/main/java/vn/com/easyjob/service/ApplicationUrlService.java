package vn.com.easyjob.service;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUrlService {
    @Autowired
    private ServletContext servletContext;

    @Autowired
    private HttpServletRequest request;


    public String getApplicationUrl() {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + servletContext.getContextPath();
    }
}
