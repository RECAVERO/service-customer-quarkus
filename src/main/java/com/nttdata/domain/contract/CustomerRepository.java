package com.nttdata.domain.contract;

import com.nttdata.domain.models.CustomerDto;
import com.nttdata.infraestructure.entity.Customer;
import java.util.List;


public interface CustomerRepository {
  List<Customer> getAllCustomer();
  Customer getByIdCustomer(Long id);
  Customer addCustomer(CustomerDto customerDto);

  List<Customer> updateCustomerById(Long id,CustomerDto customerDto);
  List<Customer> deleteCustomerById(Long id);
}
