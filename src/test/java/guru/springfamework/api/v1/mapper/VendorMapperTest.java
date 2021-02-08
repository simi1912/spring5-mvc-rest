package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendorMapperTest {

    VendorMapper mapper;

    @Before
    public void setUp(){
        mapper = VendorMapper.INSTANCE;
    }

    @Test
    public void testConvertToVendorDTO(){
        Vendor vendor = new Vendor();
        vendor.setName("Foo");

        VendorDTO vendorDTO = mapper.vendorToVendorDTO(vendor);

        assertEquals(vendor.getName(), vendorDTO.getName());
    }

    @Test
    public void testConvertToVendor(){
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Bar");

        Vendor vendor = mapper.vendorDtoToVendor(vendorDTO);

        assertEquals(vendorDTO.getName(), vendor.getName());
    }
}
