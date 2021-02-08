package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import guru.springfamework.services.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    private static final String BASE_URL = "/api/v1/customers/";
    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(RestResponseEntityExceptionHandler.class).build();
    }

    @Test
    public void getAllCustomers() throws Exception {
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstname("firstName1");
        customerDTO1.setLastname("lastName1");

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setFirstname("firstName2");
        customerDTO2.setLastname("lastName2");

        List<CustomerDTO> customerDTOS = Arrays.asList(customerDTO1, customerDTO2);

        when(customerService.getAllCustomer()).thenReturn(customerDTOS);

        mockMvc.perform(get(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void getCustomerById() throws Exception{
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstname("firstName1");
        customerDTO1.setLastname("lastName1");

        when(customerService.getCustomerById(any())).thenReturn(customerDTO1);

        mockMvc.perform(get(BASE_URL + "1")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("firstName1")));
    }

    @Test
    public void createNewCustomer() throws Exception {
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstname("firstName1");
        customerDTO1.setLastname("lastName1");

        CustomerDTO returnedDTO = new CustomerDTO();
        returnedDTO.setFirstname("firstName1");
        returnedDTO.setLastname("lastName1");

        when(customerService.createNewCustomer(customerDTO1)).thenReturn(returnedDTO);

        mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(customerDTO1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo( returnedDTO.getFirstname() )))
                .andExpect(jsonPath("$.customer_url", equalTo( returnedDTO.getCustomerUrl() )));
    }

    @Test
    public void testUpdateCustomer() throws Exception{
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstname("firstName1");
        customerDTO1.setLastname("lastName1");

        CustomerDTO returnedDTO = new CustomerDTO();
        returnedDTO.setFirstname("firstName1");
        returnedDTO.setLastname("lastName1");
        returnedDTO.setCustomerUrl(BASE_URL + "1");

        when(customerService.saveCustomerByDTO(anyLong(), any())).thenReturn(returnedDTO);

        mockMvc.perform(put(BASE_URL + "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(customerDTO1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(customerDTO1.getFirstname())))
                .andExpect(jsonPath("$.lastname", equalTo(customerDTO1.getLastname())))
                .andExpect(jsonPath("$.customer_url", equalTo(returnedDTO.getCustomerUrl())));

    }

    @Test
    public void testPatchCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname("firstName1");

        CustomerDTO returnedDTO = new CustomerDTO();
        returnedDTO.setFirstname("firstName1");
        returnedDTO.setLastname("lastName1");
        returnedDTO.setCustomerUrl(BASE_URL + "1");

        when(customerService.patchCustomer(any(), any())).thenReturn(returnedDTO);

        mockMvc.perform(patch(BASE_URL + "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("firstName1")))
                .andExpect(jsonPath("$.lastname", equalTo("lastName1")))
                .andExpect(jsonPath("$.customer_url", equalTo(BASE_URL + "1")));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete(BASE_URL + "1")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService, times(1)).deleteCustomerById(1L);
    }

    @Test
    public void testGetByNameNotFound() throws Exception {
        when(customerService.getCustomerById(any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(BASE_URL+"/100")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}