package com.nttdata.btask.interfaces;

import com.nttdata.domain.models.CustomerDto;
import com.nttdata.infraestructure.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
  List<Customer> getAllCustomer();
  Customer getByIdCustomer(Long id);

  Customer addCustomer(CustomerDto customerDto);

  List<Customer> updateCustomerById(Long id,CustomerDto customerDto);
  List<Customer> deleteCustomerById(Long id);
}
