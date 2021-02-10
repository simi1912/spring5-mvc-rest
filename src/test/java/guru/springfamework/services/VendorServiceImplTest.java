package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
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

public class VendorServiceImplTest {

    private static final String BASE_URL = "/api/v1/vendors";
    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(vendorRepository);
    }

    @Test
    public void getAllVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setName("Foo");
        Vendor vendor2 = new Vendor();
        vendor2.setName("Foo");
        List<Vendor> vendors = Arrays.asList(vendor1, vendor2);

        when(vendorRepository.findAll()).thenReturn(vendors);

        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        assertEquals(vendors.size(), vendorDTOS.size());
    }

    @Test
    public void createNewVendor(){
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Foo");
        Vendor savedVendor = new Vendor();
        savedVendor.setId(1L);
        savedVendor.setName("Foo");

        when(vendorRepository.save(any())).thenReturn(savedVendor);

        VendorDTO savedDto = vendorService.createNewVendor(vendorDTO);

        assertEquals(savedVendor.getName(), savedDto.getName());
        assertEquals(BASE_URL+"/"+savedVendor.getId(), savedDto.getVendorUrl());
    }

    @Test
    public void deleteById(){
        vendorService.deleteById(1L);
        verify(vendorRepository, times(1)).deleteById(1L);
    }

    @Test
    public void getVendorById(){
        Vendor returnedVendor = new Vendor();
        returnedVendor.setId(1L);
        returnedVendor.setName("Foo");

        when(vendorRepository.findById(1L)).thenReturn(Optional.of(returnedVendor));

        VendorDTO vendorDTO = vendorService.getVendorById(1L);

        assertEquals(returnedVendor.getName(), vendorDTO.getName());
        assertEquals(BASE_URL+"/1", vendorDTO.getVendorUrl());
    }

    @Test
    public void putVendor(){
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Foo");

        Vendor returnedVendor = new Vendor();
        returnedVendor.setId(1L);
        returnedVendor.setName("Foo");

        when(vendorRepository.save(any())).thenReturn(returnedVendor);

        VendorDTO returnedVendorDTO = vendorService.putVendor(1L, vendorDTO);

        assertEquals(vendorDTO.getName(), returnedVendorDTO.getName());
        assertEquals(BASE_URL+"/1", returnedVendorDTO.getVendorUrl());
    }
}