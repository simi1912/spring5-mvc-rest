package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.services.VendorService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorsControllerTest {

    private static final String BASE_URL = "/api/v1/vendors";
    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorsController vendorsController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Object[] controllers;
        mockMvc = MockMvcBuilders.standaloneSetup(vendorsController)
                .setControllerAdvice(RestResponseEntityExceptionHandler.class).build();
    }

    @Test
    public void getAllVendors() throws Exception {
        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO1.setName("Foo");
        VendorDTO vendorDTO2 = new VendorDTO();
        vendorDTO2.setName("Foo");
        List<VendorDTO> vendorDTOS = Arrays.asList(vendorDTO1, vendorDTO2);

        when(vendorService.getAllVendors()).thenReturn(vendorDTOS);

        mockMvc.perform(get(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }
}