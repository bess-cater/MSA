package com.msa.accounts.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import com.msa.accounts.DTO.LoansDto;

import jakarta.validation.constraints.Pattern;

@FeignClient(name = "loans", fallback = LoansFallback.class)
public interface LoansFeignService {
    // has to match with real method inside the cards service!
    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam("mobileNumber") 
    String mobileNumber, @RequestHeader("msa-correlation-id") String correlationID);

}
