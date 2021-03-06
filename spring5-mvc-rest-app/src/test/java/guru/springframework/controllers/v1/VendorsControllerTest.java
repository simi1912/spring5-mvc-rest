package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.services.VendorService;
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

import static guru.springframework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void setUp(){
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
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void saveVendor() throws Exception{
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Foo");

        VendorDTO savedVendor = new VendorDTO();
        savedVendor.setName("Foo");
        savedVendor.setVendorUrl(BASE_URL+"/99");

        when(vendorService.createNewVendor(any())).thenReturn(savedVendor);

        mockMvc.perform(post(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(savedVendor.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(savedVendor.getVendorUrl())));
    }

    @Test
    public void deleteVendor() throws Exception{
        mockMvc.perform(delete(BASE_URL+"/1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService, times(1)).deleteById(1L);
    }

    @Test
    public void getVendor() throws Exception{
        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO1.setName("Foo");
        vendorDTO1.setVendorUrl(BASE_URL+"/1");

        when(vendorService.getVendorById(1L)).thenReturn(vendorDTO1);

        mockMvc.perform(get(BASE_URL+"/1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO1.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorDTO1.getVendorUrl())));

    }

    @Test
    public void patchVendor() throws Exception{
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Foo");

        VendorDTO savedVendor = new VendorDTO();
        savedVendor.setName("Foo");
        savedVendor.setVendorUrl(BASE_URL+"/1");

        when(vendorService.patchVendor(1L, vendorDTO)).thenReturn(savedVendor);

        mockMvc.perform(patch(BASE_URL+"/1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(savedVendor.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(savedVendor.getVendorUrl())));

    }

    @Test
    public void putVendor() throws Exception{
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Foo");

        VendorDTO savedVendor = new VendorDTO();
        savedVendor.setName("Foo");
        savedVendor.setVendorUrl(BASE_URL+"/1");

        when(vendorService.putVendor(1L, vendorDTO)).thenReturn(savedVendor);

        mockMvc.perform(put(BASE_URL+"/1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(savedVendor.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(savedVendor.getVendorUrl())));
    }

}