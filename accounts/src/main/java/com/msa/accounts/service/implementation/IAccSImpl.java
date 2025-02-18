package com.msa.accounts.service.implementation;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.msa.accounts.DTO.AccountsDTO;
import com.msa.accounts.DTO.CustomerDTO;
import com.msa.accounts.constants.accConstants;
import com.msa.accounts.entity.Accounts;
import com.msa.accounts.entity.Customer;
import com.msa.accounts.exception.CustomerAlreadyExistsException;
import com.msa.accounts.exception.ResourceNotFoundException;
import com.msa.accounts.mapper.AccMapper;
import com.msa.accounts.mapper.CustomerMapper;
import com.msa.accounts.repos.AccountsRepo;
import com.msa.accounts.repos.CustomerRepo;
import com.msa.accounts.service.IAccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class IAccSImpl implements IAccountService{

    private AccountsRepo accRepo;
    private CustomerRepo customerRepo;
    //! There should be some mapping between DTO and Entity!
    public void createAccount(CustomerDTO cusdto){
    Customer customer =  CustomerMapper.mapToCustomer(cusdto, new Customer());
    Optional<Customer> cyss = customerRepo.findByMobileNumber(customer.getMobileNumber());
    if (cyss.isPresent()){
        throw new CustomerAlreadyExistsException("customer with this number exists!" + cusdto.getMobileNumber());
    }
    // customer.setCreatedAt(LocalDateTime.now());
    // customer.setCreatedBy(("ADMIN"));
    Customer savedCustomer = customerRepo.save(customer);
    accRepo.save(createNewAccount(savedCustomer));
    }

    public CustomerDTO findAccount(String mobileNumber){
        Customer cust = customerRepo.findByMobileNumber(mobileNumber).orElseThrow( () ->
        new ResourceNotFoundException("Customer", "mobile number", mobileNumber)
        );

        Accounts acc = accRepo.findByCustomerId(cust.getCustomerId()).orElseThrow( () ->
        new ResourceNotFoundException("Account", "mobile number", mobileNumber)
        );

        CustomerDTO custDTO = CustomerMapper.mapToCustomerDto(cust, new CustomerDTO());
        
        AccountsDTO accDTO = AccMapper.mapToAccountsDto(acc, new AccountsDTO());
        custDTO.setAccountDTO(accDTO);
        return custDTO;

    }

    public boolean updateAccount(CustomerDTO customerDto) {
        boolean isUpdated = false;
        AccountsDTO accountsDto = customerDto.getAccountDTO();
        if(accountsDto !=null ){
            System.out.println("HEHHEH");
            Accounts accounts = accRepo.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccMapper.mapToAccounts(accountsDto, accounts);
            accounts = accRepo.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepo.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepo.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(accConstants.SAVINGS);
        newAccount.setBranchAddress(accConstants.ADDRESS);
        // newAccount.setCreatedAt(LocalDateTime.now());
        // newAccount.setCreatedBy(("ADMIN"));
        return newAccount;
    }

    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accRepo.deleteByCustomerId(customer.getCustomerId());
        customerRepo.deleteById(customer.getCustomerId());
        return true;
    }


}
