package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.VendorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import guru.springframework.domain.Vendor;

@Mapper
public interface VendorMapper {

    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    Vendor vendorDtoToVendor(VendorDTO vendorDTO);

    VendorDTO vendorToVendorDTO(Vendor vendor);

}