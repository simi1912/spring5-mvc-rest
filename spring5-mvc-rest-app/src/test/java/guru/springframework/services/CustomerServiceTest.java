package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void getAllCustomer() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setLastname("lastName1");
        customer1.setFirstname("firstName1");
        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setLastname("lastName2");
        customer2.setFirstname("firstName2");
        List<Customer> customers = Arrays.asList(customer1, customer2);

        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> customerDTOs = customerService.getAllCustomer();

        assertEquals(2, customerDTOs.size());
    }

    @Test
    public void getCustomerById(){
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setLastname("lastName1");
        customer1.setFirstname("firstName1");

        when(customerRepository.findById(any())).thenReturn(Optional.of(customer1));

        CustomerDTO customerDTO = customerService.getCustomerById(1L);

        assertEquals("lastName1", customerDTO.getLastname());
    }

    @Test
    public void createNewCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("firstName1");
        customerDTO.setLastname("lastName1");

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setLastname("lastName1");
        savedCustomer.setFirstname("firstName1");

        when(customerRepository.save(any())).thenReturn(savedCustomer);

        CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);

        assertEquals("firstName1", savedDto.getFirstname());
        assertEquals("/api/v1/customers/1", savedDto.getCustomerUrl());
    }

    @Test
    public void saveCustomerByDTO(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("firstName1");
        customerDTO.setLastname("lastName1");

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setLastname("lastName1");
        savedCustomer.setFirstname("firstName1");

        when(customerRepository.save(any())).thenReturn(savedCustomer);

        CustomerDTO savedDto = customerService.putVendor(1L, customerDTO);

        assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
        assertEquals("/api/v1/customers/1", savedDto.getCustomerUrl());
    }

    @Test
    public void deleteCustomerById(){
        customerService.deleteCustomerById(1L);

        verify(customerRepository, times(1)).deleteById(1L);
    }
}