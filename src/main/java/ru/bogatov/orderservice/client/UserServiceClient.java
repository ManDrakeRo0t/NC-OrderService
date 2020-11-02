package ru.bogatov.orderservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.bogatov.orderservice.dto.Customer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
public class UserServiceClient {

    private RestTemplate restTemplate;
    private String url = "http://localhost:8091/customers";

    public UserServiceClient(@Autowired RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


    public List<Customer> getCustomers() throws URISyntaxException, JsonProcessingException {
        try{
            return new ObjectMapper().readValue(
                    Objects.requireNonNull(restTemplate.getForObject(new URI(url), String.class))
                    , new TypeReference<List<Customer>>() { }
            );
        } catch (URISyntaxException | JsonProcessingException e){
            e.printStackTrace();
            throw e;
        }
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
