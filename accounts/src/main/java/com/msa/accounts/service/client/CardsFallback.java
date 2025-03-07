package com.msa.accounts.service.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.msa.accounts.DTO.CardsDto;
import com.msa.accounts.DTO.LoansDto;

@Component
public class CardsFallback implements CardsFeignService{

    @Override
    public ResponseEntity<CardsDto> fetchCardDetails(String mobileNumber, String correlationID) {
        return null;
    }

   
}
