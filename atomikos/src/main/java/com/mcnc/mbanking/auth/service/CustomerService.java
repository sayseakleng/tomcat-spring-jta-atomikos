package com.mcnc.mbanking.auth.service;

import com.mcnc.mbanking.auth.domain.Customer;

public interface CustomerService {
	Customer getCustomer(Long customerId);
	Customer getCustomerByDetail(Customer customer);
}
