package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;

import java.util.List;

public interface VendorService {

    List<VendorDTO> getAllVendors();

}
