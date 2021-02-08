package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private static final String BASE_URL = "/api/v1/vendors/";
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper = VendorMapper.INSTANCE;

    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll().stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setCustomerUrl(getVendorUrl(vendor));
                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor customer = vendorMapper.vendorDtoToVendor(vendorDTO);
        return saveAndReturnDTO(customer);
    }

    private VendorDTO saveAndReturnDTO(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO returnedDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        returnedDTO.setCustomerUrl(getVendorUrl(savedVendor));
        return returnedDTO;
    }

    private String getVendorUrl(Vendor vendor) {
        return BASE_URL + vendor.getId();
    }
}
