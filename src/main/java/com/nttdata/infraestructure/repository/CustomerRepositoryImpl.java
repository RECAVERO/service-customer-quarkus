package com.nttdata.infraestructure.repository;

import com.nttdata.domain.contract.CustomerRepository;
import com.nttdata.domain.models.CustomerDto;
import com.nttdata.infraestructure.entity.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CustomerRepositoryImpl implements CustomerRepository {
  @Override
  public List<Customer> getAllCustomer() {
    List<Customer> collect = Customer.listAll();
    List<Customer> list = collect.stream()
        .filter(customer -> customer.getActive().equals("S"))
        .collect(Collectors.toList());
    return list;
  }

  @Override
  public List<Customer> getByIdCustomer(Long id) {
    List<Customer> collect = new ArrayList<>();
    Customer aux = Customer.findById(id);

    if(aux == null){
      return collect;
    }else{
      collect.add(aux);
      List<Customer> list = collect.stream()
          .filter(customer -> customer.getActive().equals("S"))
          .collect(Collectors.toList());
      return list;
    }

  }

  @Override
  @Transactional
  public Customer addCustomer(CustomerDto customerDto) {
    Customer customer = new Customer();
    customer.setName(customerDto.getName());
    customer.setLastName(customerDto.getLastName());
    customer.setNroDocument(customerDto.getNroDocument());
    customer.setTypeCustomer(customerDto.getTypeCustomer());
    customer.setTypeDocument(customerDto.getTypeDocument());
    customer.setCreated_datetime(this.getDateNow());
    customer.setUpdated_datetime(this.getDateNow());
    customer.setActive("S");
    customer.persist();
    return customer;
  }

  @Override
  @Transactional
  public Customer updateCustomerById(Long id, CustomerDto customerDto) {
    Customer customer = new Customer();
    Customer customerOp = Customer.findById(id);

    if(customerOp == null){
      throw new NullPointerException("customer not found");
    }else{

      customer.setName(customerDto.getName());
      customer.setLastName(customerDto.getLastName());
      customer.setNroDocument(customerDto.getNroDocument());
      customer.setTypeCustomer(customerDto.getTypeCustomer());
      customer.setTypeDocument(customerDto.getTypeDocument());
      customer.setUpdated_datetime(this.getDateNow());
      customer.persist();
    }

    return customerOp;
  }

  @Override
  @Transactional
  public List<Customer> deleteCustomerById(Long id) {
    List<Customer> collect = new ArrayList<>();
    Customer customerOp = Customer.findById(id);

    if(customerOp == null){
      return collect;
    }else{
      Customer customer = new Customer();
      customerOp.setUpdated_datetime(this.getDateNow());
      customerOp.setActive("N");
      customerOp.persist();
      collect.add(customerOp);

    }

    return collect;
  }

  private static String getDateNow(){
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return formatter.format(date).toString();
  }
}
