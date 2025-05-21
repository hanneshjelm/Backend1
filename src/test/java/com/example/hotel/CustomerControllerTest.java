package com.example.hotel;

import com.example.hotel.models.Customer;
import com.example.hotel.repos.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CustomerControllerTest
{

    @Autowired
    private MockMvc mockMvc;

@Autowired
    private CustomerRepository customerRepository;





    @BeforeEach
            public void setUp()
    {
        customerRepository.deleteAll();
        customerRepository.save(new Customer("James", "jonnyson@gmail.com"));
    }




    @Test
    public void getAllCustomersTest() throws Exception
    {
        MvcResult mvcResult = mockMvc.perform(get("/api/customers/allcustomers")).andExpect(status().isOk()).andExpect((ResultMatcher) jsonPath("$.[*].name").value(containsInAnyOrder( "James"))).andReturn();


    }



}
