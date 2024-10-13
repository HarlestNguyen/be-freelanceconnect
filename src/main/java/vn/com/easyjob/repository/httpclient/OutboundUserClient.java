package vn.com.easyjob.repository.httpclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.com.easyjob.model.dto.OutboundUserWithTokenReponese;
@Component
@FeignClient(name = "outbound-user-client", url = "https://www.googleapis.com")
public interface OutboundUserClient {
    @GetMapping(value = "/oauth2/v1/userinfo")
    OutboundUserWithTokenReponese GetUserInfo(@RequestParam("alt") String code,
                                              @RequestParam("access_token") String accessToken
                                                );

}
