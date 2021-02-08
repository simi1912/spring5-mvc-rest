package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorsListDTO;
import guru.springfamework.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vendors")
public class VendorsController {

    private final VendorService vendorService;

    public VendorsController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorsListDTO getAllVendors(){
        return new VendorsListDTO(vendorService.getAllVendors());
    }

}
