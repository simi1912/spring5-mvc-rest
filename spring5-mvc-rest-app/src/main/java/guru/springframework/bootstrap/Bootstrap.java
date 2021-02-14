package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.CategoryRepository;

@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository,
                     CustomerRepository customerRepository,
                     VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args){
        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Categories Loaded = " + categoryRepository.count() );
    }

    private void loadCustomers() {
        Customer customer1 = new Customer();
        customer1.setFirstname("Joe");
        customer1.setLastname("Newman");

        Customer customer2 = new Customer();
        customer2.setFirstname("Michael");
        customer2.setLastname("Lachappele");

        Customer customer3 = new Customer();
        customer3.setFirstname("David");
        customer3.setLastname("Winter");

        Customer customer4 = new Customer();
        customer4.setFirstname("Anne");
        customer4.setLastname("Hine");

        Customer customer5 = new Customer();
        customer5.setFirstname("Alice");
        customer5.setLastname("Eastman");

        Customer customer6 = new Customer();
        customer6.setFirstname("Freddy");
        customer6.setLastname("Meyers");

        Customer customer7 = new Customer();
        customer7.setFirstname("raj");
        customer7.setLastname("ram");

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
        customerRepository.save(customer4);
        customerRepository.save(customer5);
        customerRepository.save(customer6);
        customerRepository.save(customer7);

        System.out.println("Customers Loaded = " + categoryRepository.count() );
    }

    private void loadVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setName("Western Tasty Fruits Ltd.");
        Vendor vendor2 = new Vendor();
        vendor2.setName("Exotic Fruits Company");
        Vendor vendor3 = new Vendor();
        vendor3.setName("Home Fruits");
        Vendor vendor4 = new Vendor();
        vendor4.setName("Fun Fresh Fruits Ltd.");
        Vendor vendor5 = new Vendor();
        vendor5.setName("Nuts for Nuts Company");

        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);
        vendorRepository.save(vendor3);
        vendorRepository.save(vendor4);
        vendorRepository.save(vendor5);

        System.out.println("Vendors Loaded = " + vendorRepository.count());
    }
}