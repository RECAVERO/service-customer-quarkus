package com.nttdata.btask.services;

import com.nttdata.btask.interfaces.CustomerService;
import com.nttdata.domain.contract.CustomerRepository;
import com.nttdata.domain.models.CustomerDto;
import com.nttdata.infraestructure.entity.Customer;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
@ApplicationScoped
public class CustomerServiceImpl implements CustomerService {
  private final CustomerRepository customerRepository;

  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public List<Customer> getAllCustomer() {
    return customerRepository.getAllCustomer();
  }

  @Override
  public List<Customer> getByIdCustomer(Long id) {
    return customerRepository.getByIdCustomer(id);
  }

  @Override
  public Customer addCustomer(CustomerDto customerDto) {
    return customerRepository.addCustomer(customerDto);
  }

  @Override
  public Customer updateCustomerById(Long id, CustomerDto customerDto) {
    return customerRepository.updateCustomerById(id, customerDto);
  }

  @Override
  public List<Customer> deleteCustomerById(Long id) {
    return customerRepository.deleteCustomerById(id);
  }
}
