package com.ecommerce.configurations;

import com.ecommerce.domains.*;
import com.ecommerce.domains.enums.PaymentStatus;
import com.ecommerce.domains.enums.UserType;
import com.ecommerce.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@Configuration
public class Installation implements CommandLineRunner {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public void run(String... args) throws Exception {

        Category cat1 = new Category(null, "Informatica");
        Category cat2 = new Category(null, "Escritórioo");

        Product p1 = new Product(null, "Computador", 3000.00);
        Product p2 = new Product(null, "Impressora", 800.00);
        Product p3 = new Product(null, "Mouse", 80.00);



        cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProducts().addAll(Arrays.asList(p2));

        p1.getCategories().addAll(Arrays.asList(cat1));
        p2.getCategories().addAll(Arrays.asList(cat1, cat2));
        p3.getCategories().addAll(Arrays.asList(cat1));

        categoryRepository.saveAll(Arrays.asList(cat1, cat2));
        productRepository.saveAll(Arrays.asList(p1, p2, p3));

        State std1 = new State(null, "Santa Catarina");
        State std2 = new State(null, "Parana");

        City ct1 = new City(null, "Joinville", std1);
        City ct2 = new City(null, "Florianópolis", std1);
        City ct3 = new City(null, "Curitiba", std2);
        City ct4 = new City(null, "Ponta Grossa", std2);

        std1.getCities().addAll(Arrays.asList(ct1, ct2));
        std2.getCities().addAll(Arrays.asList(ct3, ct4));

        stateRepository.saveAll(Arrays.asList(std1, std2));
        cityRepository.saveAll(Arrays.asList(ct1, ct2, ct3, ct4));

        User u1 = new User(null, "Mary of Help", "maria3@db.com",
                "2254879642", UserType.CLIENT_PERSONAL_ENTITY);

        u1.getPhones().addAll(Arrays.asList("213225566", "254136554"));

        Address ad1 = new Address(null, "Rua 40", "300", "Jardins",
                "326598", u1, ct1);
        Address ad2 = new Address(null, "Rua 60", "300", "Laranjeiras",
                "326599", u1, ct1);

        u1.getAddressList().addAll(Arrays.asList(ad1, ad2));

        userRepository.saveAll(Arrays.asList(u1));
        addressRepository.saveAll(Arrays.asList(ad1, ad2));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Orders odr1 = new Orders(null, sdf.parse("10/06/2020 10:32"), u1, ad1);
        Orders odr2 = new Orders(null, sdf.parse("10/06/2020 10:32"), u1, ad2);

        Payment pay1 = new CardPayment(null, PaymentStatus.PAID, odr1, 6);
        odr1.setPayment(pay1);

        Payment pay2 = new BilletPayment(null, PaymentStatus.PENDING, odr2,
                sdf.parse("15/06/2020 10:00:00"), null);
        odr2.setPayment(pay2);

        u1.getOrders().addAll(Arrays.asList(odr1, odr2));

        ordersRepository.saveAll(Arrays.asList(odr1, odr2));
        paymentRepository.saveAll(Arrays.asList(pay1, pay2));

    }
}