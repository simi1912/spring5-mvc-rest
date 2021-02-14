package guru.springframework.services;

import guru.springframework.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {

    List<VendorDTO> getAllVendors();

    VendorDTO createNewVendor(VendorDTO vendorDTO);

    void deleteById(Long id);

    VendorDTO getVendorById(Long id);

    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);

    VendorDTO putVendor(Long id, VendorDTO vendorDTO);
}