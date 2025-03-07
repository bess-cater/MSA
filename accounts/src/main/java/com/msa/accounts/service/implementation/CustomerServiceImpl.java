package com.msa.accounts.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.msa.accounts.DTO.AccountsDTO;
import com.msa.accounts.DTO.CardsDto;
import com.msa.accounts.DTO.CustomerDetailsDTO;
import com.msa.accounts.DTO.LoansDto;
import com.msa.accounts.entity.Accounts;
import com.msa.accounts.entity.Customer;
import com.msa.accounts.exception.ResourceNotFoundException;
import com.msa.accounts.mapper.AccMapper;
import com.msa.accounts.mapper.CustomerMapper;
import com.msa.accounts.repos.AccountsRepo;
import com.msa.accounts.repos.CustomerRepo;
import com.msa.accounts.service.ICustomerService;
import com.msa.accounts.service.client.CardsFeignService;
import com.msa.accounts.service.client.LoansFeignService;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountsRepo accountsRepository;
    private CustomerRepo customerRepository;
    private CardsFeignService cardsFeignClient;
    private LoansFeignService loansFeignClient;
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDTO fetchCustomerDetails(String mobileNumber, String correlationID) {
        logger.debug("Before Feign call - mobileNumber: {}", mobileNumber);
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDTO customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDTO());
        customerDetailsDto.setAccountsDto(AccMapper.mapToAccountsDto(accounts, new AccountsDTO()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber, correlationID);
        if(null!= loansDtoResponseEntity){customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());}
        
        // logger.debug("After Feign call - Response: {}", loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber, correlationID);
        if(null!=cardsDtoResponseEntity){customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());}
        

        return customerDetailsDto;

    }
}