package com.nttdata.application.rest;

import com.nttdata.btask.interfaces.CustomerService;
import com.nttdata.domain.models.CustomerDto;
import com.nttdata.domain.models.ResponseDto;
import com.nttdata.infraestructure.entity.Customer;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * clase que tiene los endpoints con las funcionalidades :
 *  - listado de todos los registros activos
 *  - busqueda por id de un customer
 *  - creacion de un customer
 *  - actualizado la data de customer
 *  - borrar logico solo quitarle la actividad al customer para que no se muestre
 */

@Path("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GET
    public Response getAllCustomer() {
        ResponseDto responseDto = new ResponseDto();
        List<Customer> collectCustomer = customerService.getAllCustomer();
        responseDto.setStatus("204");
        responseDto.setMessage("customer not found");
        if(collectCustomer.size() == 0){
            return Response.ok(responseDto).status(204).build();
        }else{
            List<Customer> list = collectCustomer.stream()
                .filter(customer -> customer.getActive().equals("S"))
                .peek(c->{
                    responseDto.setStatus("200");
                    responseDto.setMessage("Se proceso Correctamente");
                })
                .collect(Collectors.toList());

            responseDto.setCustomer(list);
            return Response.ok(responseDto).build();

        }


    }

    @GET
    @Path("{id}")
    public Response getByIdCustomer(@PathParam("id") Long id) {

        ResponseDto responseDto = new ResponseDto();
        Customer customer = this.customerService.getByIdCustomer(id);
        responseDto.setStatus("204");
        responseDto.setMessage("customer not found");
        if(customer == null){
            return Response.ok(responseDto).status(204).build();
        }else{

            List<Customer> collect = new ArrayList<>();
            collect.add(customer);
            collect = collect.stream().filter(c -> c.getActive().equals("S"))
                .peek(c->{
                    responseDto.setStatus("200");
                    responseDto.setMessage("Se proceso Correctamente");
                })
                .collect(Collectors.toList());
            responseDto.setCustomer(collect);
            return Response.ok(responseDto).build();

        }

    }

    @POST
    public Response addCustomer(CustomerDto customerDto) {
        if(customerDto == null){
            return Response.status(415).build();
        }else{
            return Response.ok(this.customerService.addCustomer(customerDto)).status(201).build();
        }

    }

    @PUT
    @Path("{id}")
    public Response updateCustomer(@PathParam("id") Long id, CustomerDto customerDto) {
        ResponseDto responseDto = new ResponseDto();
        List<Customer> customer = this.customerService.updateCustomerById(id, customerDto);
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

    @DELETE
    @Path("{id}")
    public Response deleteCustomer(@PathParam("id") Long id) {
        ResponseDto responseDto = new ResponseDto();
        List<Customer> customer = this.customerService.deleteCustomerById(id);

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

    private static String getDateNow(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date).toString();
    }
}
