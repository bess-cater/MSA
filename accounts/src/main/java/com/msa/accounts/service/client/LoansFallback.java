package com.msa.accounts.service.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.msa.accounts.DTO.LoansDto;

@Component
public class LoansFallback implements LoansFeignService{

    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String mobileNumber, String correlationID) {
        return null;
    }

}
