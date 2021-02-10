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
                    vendorDTO.setVendorUrl(getVendorUrl(vendor));
                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor customer = vendorMapper.vendorDtoToVendor(vendorDTO);
        return saveAndReturnDTO(customer);
    }



    @Override
    public void deleteById(Long id) {
        vendorRepository.deleteById(id);
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map( vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor));
                    return vendorDTO;
                })
                .orElseThrow(()-> new ResourceNotFoundException("Vendor not found"));
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map( vendor ->{
            if(vendorDTO.getName() != null)
                vendor.setName(vendorDTO.getName());

            return saveAndReturnDTO(vendor);
        }).orElseThrow( () -> new ResourceNotFoundException("Vendor not found"));
    }

    @Override
    public VendorDTO putVendor(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
        vendor.setId(id);
        return saveAndReturnDTO(vendor);
    }

    private VendorDTO saveAndReturnDTO(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO returnedDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        returnedDTO.setVendorUrl(getVendorUrl(savedVendor));
        return returnedDTO;
    }

    private String getVendorUrl(Vendor vendor) {
        return BASE_URL + vendor.getId();
    }
}
