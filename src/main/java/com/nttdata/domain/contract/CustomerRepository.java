package com.nttdata.domain.contract;

import com.nttdata.domain.models.CustomerDto;
import com.nttdata.infraestructure.entity.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
  List<Customer> getAllCustomer();
  List<Customer> getByIdCustomer(Long id);
  Customer addCustomer(CustomerDto customerDto);

  Customer updateCustomerById(Long id,CustomerDto customerDto);
  List<Customer> deleteCustomerById(Long id);
}
