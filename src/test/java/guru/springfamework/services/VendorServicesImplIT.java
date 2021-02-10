package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VendorServicesImplIT {

    private static final String BASE_URL = "/api/v1/vendors";

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    VendorRepository vendorRepository;

    VendorService vendorService;

    @Before
    public void setUp(){
        Bootstrap bootstrap = new Bootstrap(categoryRepository,
                customerRepository,
                vendorRepository);
        bootstrap.run();

        vendorService = new VendorServiceImpl(vendorRepository);
    }

    @Test
    public void patchVendorNoUpdate(){
        Long id = getVendorIdValue();

        Vendor originalVendor = vendorRepository.findById(id).get();
        assertNotNull(originalVendor);

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(null);

        VendorDTO returnedVendorDTO = vendorService.patchVendor(id, vendorDTO);

        assertEquals(originalVendor.getName(), returnedVendorDTO.getName());
        assertEquals(BASE_URL+"/"+id, returnedVendorDTO.getVendorUrl());
    }

    @Test
    public void patchVendorUpdatedName(){
        Long id = getVendorIdValue();

        Vendor originalVendor = vendorRepository.findById(id).get();
        assertNotNull(originalVendor);

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Foo");

        VendorDTO returnedVendorDTO = vendorService.patchVendor(id, vendorDTO);

        assertEquals(vendorDTO.getName(), returnedVendorDTO.getName());
        assertEquals(BASE_URL+"/"+id, returnedVendorDTO.getVendorUrl());
    }

    private Long getVendorIdValue(){
        List<Vendor> vendors = vendorRepository.findAll();

        return vendors.get(0).getId();
    }

}
