package com.nttdata;

import com.nttdata.application.rest.CustomerController;
import com.nttdata.domain.models.CustomerDto;
import com.nttdata.domain.models.ResponseDto;
import com.nttdata.infraestructure.entity.Customer;
import com.nttdata.infraestructure.repository.CustomerRepositoryImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;


@QuarkusTest
public class CustomerTest {
  private final CustomerController customerController;
  private Customer customer;
  private CustomerDto customerDto;

  @InjectMock
  CustomerRepositoryImpl customerRepository;

  public CustomerTest(CustomerController customerController) {
    this.customerController = customerController;
  }
  @BeforeEach
  public void setCustomerInit(){
    customer = new Customer();
    customer.setName("estefania");
    customer.setLastName("gotia");
    customer.setNroDocument(44444444L);
    customer.setTypeCustomer(1);
    customer.setTypeDocument(1);
    customer.setCreated_datetime("2023-02-10 08:10:10");
    customer.setUpdated_datetime("2023-02-10 08:10:10");
    customer.setActive("S");

    customerDto = new CustomerDto();
    customerDto.setId(1L);
    customerDto.setName("estefania");
    customerDto.setLastName("gotia");
    customerDto.setNroDocument(44444444L);
    customerDto.setTypeCustomer(1);
    customerDto.setTypeDocument(1);
  }

  @Test
  public void getAllCustomerTestNotFound(){
    List<Customer> listCustomer=new ArrayList<>();
    Mockito.when(customerRepository.getAllCustomer()).thenReturn(listCustomer);
    Response responseDto = customerController.getAllCustomer();
    Assertions.assertEquals(0,listCustomer.size());
    Assertions.assertEquals(responseDto.getStatus(),204);
  }
  @Test
  public void getAllCustomerTestOk(){
    List<Customer> listCustomer=new ArrayList<>();
    listCustomer.add(customer);
    Mockito.when(customerRepository.getAllCustomer()).thenReturn(listCustomer);
    Response responseDto = customerController.getAllCustomer();
    Assertions.assertEquals(listCustomer.size(),1);
    Assertions.assertEquals(responseDto.getStatus(),200);
  }

  @Test
  public void getByIdCustomerTestNull(){
    Customer customer = null;
    Mockito.when(customerRepository.getByIdCustomer(1L)).thenReturn(customer);
    customer = customerRepository.getByIdCustomer(1L);
    Response responseDto = customerController.getByIdCustomer(1L);
    Assertions.assertNull(customer);
    Assertions.assertEquals(responseDto.getStatus(), 204);

  }



  @Test
  public void getByIdCustomerTestNotNull(){
    Mockito.when(customerRepository.getByIdCustomer(1L)).thenReturn(customer);
    Customer customerActual = customerRepository.getByIdCustomer(1L);
    Response responseDto = customerController.getByIdCustomer(1L);
    Assertions.assertNotNull(customerActual);
    Assertions.assertEquals("estefania", customerActual.getName());
    Assertions.assertEquals("gotia", customerActual.getLastName());
    Assertions.assertEquals(44444444L, customerActual.getNroDocument());
    Assertions.assertEquals(1, customerActual.getTypeCustomer());
    Assertions.assertEquals(1, customerActual.getTypeDocument());
    Assertions.assertEquals("2023-02-10 08:10:10", customerActual.getCreated_datetime());
    Assertions.assertEquals("2023-02-10 08:10:10", customerActual.getUpdated_datetime());
    Assertions.assertEquals("S", customerActual.getActive());
    Assertions.assertEquals(responseDto.getStatus(), 200);

  }
  @Test
  public void getByIdCustomerTestNotActive(){
    customer.setActive("N");
    List<Customer> lc = new ArrayList<>();

    Mockito.when(customerRepository.getByIdCustomer(1L)).thenReturn(customer);
    Customer customerActual = customerRepository.getByIdCustomer(1L);
    Response responseDto = customerController.getByIdCustomer(1L);
    System.out.println(responseDto.getStatus() + "---" + responseDto.getEntity() + "---" + customerActual);
    Assertions.assertNotNull(customerActual);
    Assertions.assertEquals("estefania", customerActual.getName());
    Assertions.assertEquals("gotia", customerActual.getLastName());
    Assertions.assertEquals(44444444L, customerActual.getNroDocument());
    Assertions.assertEquals(1, customerActual.getTypeCustomer());
    Assertions.assertEquals(1, customerActual.getTypeDocument());
    Assertions.assertEquals("2023-02-10 08:10:10", customerActual.getCreated_datetime());
    Assertions.assertEquals("2023-02-10 08:10:10", customerActual.getUpdated_datetime());
    Assertions.assertEquals("N", customerActual.getActive());
    Assertions.assertEquals(responseDto.getStatus(), 200);

  }

  @Test
  void createOk(){

    Mockito.when(customerRepository.addCustomer(customerDto)).thenReturn(customer);
    Response response = customerController.addCustomer(customerDto);
    Customer customerRespuesta = (Customer) response.getEntity();
    Assertions.assertNotNull(customerDto);
    Assertions.assertEquals(customerDto.getName(), customerRespuesta.getName());
    Assertions.assertEquals(customerDto.getLastName(), customerRespuesta.getLastName());
    Assertions.assertEquals(customerDto.getNroDocument(), customerRespuesta.getNroDocument());
    Assertions.assertEquals(customerDto.getTypeCustomer(), customerRespuesta.getTypeCustomer());
    Assertions.assertEquals(customerDto.getTypeDocument(), customerRespuesta.getTypeDocument());

    Assertions.assertEquals(response.getStatus(), 201);
  }

  @Test
  void createObjectNull(){
    CustomerDto customerDto = null;
    Mockito.when(customerRepository.addCustomer(customerDto)).thenReturn(customer);
    Response response = customerController.addCustomer(customerDto);
    Assertions.assertNull(customerDto);
    Assertions.assertEquals(response.getStatus(), 415);
  }
  @Test
  void updateNotExistCustomer(){
    List<Customer> listCustomer = new ArrayList<>();
    Mockito.when(customerRepository.updateCustomerById(1L,customerDto)).thenReturn(listCustomer);
    Assertions.assertEquals(listCustomer.size(), 0);
    Response response = customerController.updateCustomer(1L,customerDto);
    Assertions.assertEquals(response.getStatus(), 204);
  }

  @Test
  void updateExistCustomer(){
    List<CustomerDto> listCustomerDto = new ArrayList<>();
    List<Customer> listCustom = new ArrayList<>();
    listCustomerDto.add(customerDto);
    listCustom.add(customer);

    ResponseDto responseDto = new ResponseDto();
    responseDto.setStatus("200");
    responseDto.setMessage("Se proceso Correctamente");

    responseDto.setCustomer(listCustom);
    Mockito.when(customerRepository.updateCustomerById(1L,customerDto)).thenReturn(listCustom);
    Assertions.assertEquals(listCustom.size(), 1);
    Response response = customerController.updateCustomer(1L,customerDto);
    Assertions.assertEquals(response.getStatus(), 200);
  }

  @Test
  void deleteNotExistCustomer(){
    List<Customer> listCustomer = new ArrayList<>();

    Mockito.when(customerRepository.deleteCustomerById(1L)).thenReturn(listCustomer);


    Assertions.assertEquals(listCustomer.size(), 0);
    Response response = customerController.deleteCustomer(1L);
    Assertions.assertEquals(response.getStatus(), 204);
  }

  @Test
  void deleteExistCustomer(){
    List<Customer> listCustomer = new ArrayList<>();
    listCustomer.add(customer);

    Mockito.when(customerRepository.deleteCustomerById(1L)).thenReturn(listCustomer);


    Assertions.assertEquals(listCustomer.size(), 1);
    Response response = customerController.deleteCustomer(1L);
    Assertions.assertEquals(response.getStatus(), 200);
  }
}
