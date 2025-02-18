package com.msa.accounts.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.msa.accounts.DTO.CardsDto;

import jakarta.validation.constraints.Pattern;

@FeignClient(name= "cards")
public interface CardsFeignService {
    // has to match with real method inside the cards service!
    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam("mobileNumber") 
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") 
    String mobileNumber, @RequestHeader("msa-correlation-id") String correlationID);

}
