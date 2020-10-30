package ru.bogatov.orderservice.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.bogatov.orderservice.dto.Customer;
import ru.bogatov.orderservice.dto.CustomersListDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class UserServiceClient {

    private RestTemplate restTemplate;
    private String url = "http://localhost:8091/customers";

    public UserServiceClient(@Autowired RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


    public List<Customer> getCustomers(){
        try{
            CustomersListDto response = restTemplate.getForObject(new URI(url),CustomersListDto.class);
            assert response != null;
            return response.getCustomers();
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
        return null;
    }

    public Customer getCustomerById(String id) {
        try{
            return restTemplate.getForObject(new URI(url+"/"+id),Customer.class);
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
        return null;

    }
}
