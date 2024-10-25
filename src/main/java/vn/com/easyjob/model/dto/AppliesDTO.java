package vn.com.easyjob.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import vn.com.easyjob.util.ApplieStatusEnum;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class AppliesDTO implements Serializable {
    private String applier;
    private JobDTO job;
    private ApplieStatusEnum applieStatus;
}
