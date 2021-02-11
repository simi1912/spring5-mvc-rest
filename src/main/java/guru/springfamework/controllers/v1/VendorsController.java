package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorsListDTO;
import guru.springfamework.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"This is Vendor Controller"})
@RestController
@RequestMapping(VendorsController.BASE_URL)
public class VendorsController {

    protected static final String BASE_URL = "/api/v1/vendors";
    private final VendorService vendorService;

    public VendorsController(VendorService vendorService) {
        this.vendorService = vendorService;
    }
    @ApiOperation(value = "This will get a list of vendors")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorsListDTO getAllVendors(){
        return new VendorsListDTO(vendorService.getAllVendors());
    }

    @ApiOperation(value = "This will get individual vendors")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long id){
        return vendorService.getVendorById(id);
    }

    @ApiOperation(value = "This will create a new vendor")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO){
        return vendorService.createNewVendor(vendorDTO);
    }

    @ApiOperation(value = "This will replace the entire vendor")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO){
        return vendorService.putVendor(id, vendorDTO);
    }

    @ApiOperation(value = "This will update only the sent properties")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO){
        return vendorService.patchVendor(id, vendorDTO);
    }

    @ApiOperation(value = "This will delete a vendor")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable Long id){
        vendorService.deleteById(id);
    }
}
