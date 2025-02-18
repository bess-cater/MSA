package com.msa.accounts.DTO;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatusCode;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class ErrorDTO {
    private String apiPath;
    
    private HttpStatusCode errorCode;
    private String errorMsg;
    private LocalDateTime errorTime;

}
