package com.mcnc.mbanking.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mcnc.mbanking.auth.dao.CustomerDAO;
import com.mcnc.mbanking.auth.domain.Customer;
import com.mcnc.mbanking.auth.service.CustomerService;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDAO customerDAO;

	@Override
	public Customer getCustomer(Long customerId) {
		return customerDAO.getCustomer(customerId);
	}

	@Override
	public Customer getCustomerByDetail(Customer customer) {
		return customerDAO.getCustomerByDetail(customer);
	}

}
