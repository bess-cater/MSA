package com.msa.accounts.service;

import com.msa.accounts.DTO.CustomerDetailsDTO;

public interface ICustomerService {

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    CustomerDetailsDTO fetchCustomerDetails(String mobileNumber, String correlationID);
}