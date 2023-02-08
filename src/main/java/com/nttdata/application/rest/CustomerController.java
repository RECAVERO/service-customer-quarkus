package com.nttdata.application.rest;

import com.nttdata.btask.interfaces.CustomerService;
import com.nttdata.domain.models.CustomerDto;
import com.nttdata.domain.models.ResponseDto;
import com.nttdata.infraestructure.entity.Customer;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GET
    public Response getAllCustomer() {
        ResponseDto responseDto = new ResponseDto();
        List<Customer> customer = this.customerService.getAllCustomer();
        if(customer.size() == 0){
            responseDto.setStatus("204");
            responseDto.setMessage("customer not found");
            return Response.ok(responseDto).status(204).build();
        }else{
            responseDto.setStatus("200");
            responseDto.setMessage("Se proceso Correctamente");
            responseDto.setCustomer(customer);
            return Response.ok(responseDto).build();

        }


    }

    @GET
    @Path("{id}")
    public Response getByIdCustomer(@PathParam("id") Long id) {
        ResponseDto responseDto = new ResponseDto();

        List<Customer> customer = this.customerService.getByIdCustomer(id);

        if(customer.size() == 0){
            responseDto.setStatus("204");
            responseDto.setMessage("customer not found");
            return Response.ok(responseDto).status(204).build();

        }else{
            responseDto.setStatus("200");
            responseDto.setMessage("Se proceso Correctamente");
            responseDto.setCustomer(customer);
            return Response.ok(responseDto).build();

        }

    }

    @POST
    public Response addCustomer(CustomerDto customerDto) {
        return Response.ok(this.customerService.addCustomer(customerDto)).status(201).build();
    }

    @PUT
    @Path("{id}")
    public Response updateCustomer(@PathParam("id") Long id, CustomerDto customerDto) {
        ResponseDto responseDto = new ResponseDto();
        List<Customer> customer = this.customerService.getByIdCustomer(id);
        if(customer.size() == 0){
            responseDto.setStatus("204");
            responseDto.setMessage("customer not found");
            return Response.ok(responseDto).status(204).build();

        }else{
            responseDto.setStatus("200");
            responseDto.setMessage("Se proceso Correctamente");
            responseDto.setCustomer(customer);
            this.customerService.updateCustomerById(id, customerDto);
            return Response.ok(responseDto).build();
        }

    }

    @DELETE
    @Path("{id}")
    public Response deleteCustomer(@PathParam("id") Long id) {
        ResponseDto responseDto = new ResponseDto();
        List<Customer> customer = this.customerService.deleteCustomerById(id);

        if(customer == null){
            responseDto.setStatus("204");
            responseDto.setMessage("customer not found");
            return Response.ok(responseDto).status(204).build();

        }else{
            responseDto.setStatus("200");
            responseDto.setMessage("Se proceso Correctamente");
            responseDto.setCustomer(customer);
            return Response.ok(responseDto).build();
        }

    }
}
