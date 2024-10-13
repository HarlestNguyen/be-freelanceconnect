package vn.com.easyjob.repository.httpclient;


import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import vn.com.easyjob.model.dto.ExchangeTokenReponese;
import vn.com.easyjob.model.dto.ExchangeTokenRequest;

@Component
@FeignClient(name = "outbound-identity", url = "https://oauth2.googleapis.com")
public interface OutboundIdentityClient {
    @PostMapping(value = "/token", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ExchangeTokenReponese exchangeToken(@QueryMap ExchangeTokenRequest request);
}
