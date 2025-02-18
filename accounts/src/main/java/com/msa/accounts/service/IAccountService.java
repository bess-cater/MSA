package com.msa.accounts.service;

import com.msa.accounts.DTO.CustomerDTO;

public interface IAccountService {

    //method for creating an account
    void createAccount(CustomerDTO cusdto);

    CustomerDTO findAccount(String mobileNumber);

    boolean updateAccount(CustomerDTO cusdto);

    boolean deleteAccount(String mobileNumber);


}
