package com.ecommerce.configurations;

import com.ecommerce.domains.Address;
import com.ecommerce.domains.BilletPayment;
import com.ecommerce.domains.CardPayment;
import com.ecommerce.domains.Category;
import com.ecommerce.domains.City;
import com.ecommerce.domains.OrderItem;
import com.ecommerce.domains.Orders;
import com.ecommerce.domains.Payment;
import com.ecommerce.domains.Product;
import com.ecommerce.domains.State;
import com.ecommerce.domains.User;
import com.ecommerce.domains.enums.PaymentStatus;
import com.ecommerce.repositories.AddressRepository;
import com.ecommerce.repositories.CategoryRepository;
import com.ecommerce.repositories.CityRepository;
import com.ecommerce.repositories.OrderItemRepository;
import com.ecommerce.repositories.OrdersRepository;
import com.ecommerce.repositories.PaymentRepository;
import com.ecommerce.repositories.ProductRepository;
import com.ecommerce.repositories.StateRepository;
import com.ecommerce.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public Installation() {
    }

    @Override
    public void run(String... args) throws Exception {


        Category cat1 = new Category(null, "Informatica");
        Category cat2 = new Category(null, "Escritórioo");
        Category cat3 = new Category(null, "Cama mesa e banho");
        Category cat4 = new Category(null, "Eletrônicos");
        Category cat5 = new Category(null, "Jardinagem");
        Category cat6 = new Category(null, "Decoração");
        Category cat7 = new Category(null, "Perfumaria");

        Product p1 = new Product(null, "Computador", 3000.00);
        Product p2 = new Product(null, "Impressora", 800.00);
        Product p3 = new Product(null, "Mouse", 80.00);
        Product p4 = new Product(null, "Mesa de escritório", 3000.00);
        Product p5 = new Product(null, "Toalha", 800.00);
        Product p6 = new Product(null, "Colcha", 80.00);
        Product p7 = new Product(null, "TV true coloe", 3000.00);
        Product p8 = new Product(null, "Roçadeira", 800.00);
        Product p9 = new Product(null, "Abajour", 80.00);
        Product p10 = new Product(null, "Pendente", 3000.00);
        Product p11 = new Product(null, "Shampoo", 800.00);

        cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProducts().addAll(Arrays.asList(p2, p4));
        cat3.getProducts().addAll(Arrays.asList(p5, p6));
        cat4.getProducts().addAll(Arrays.asList(p1, p2, p3, p7));
        cat5.getProducts().addAll(Arrays.asList(p8));
        cat6.getProducts().addAll(Arrays.asList(p9, p10));
        cat7.getProducts().addAll(Arrays.asList(p11));

        p1.getCategories().addAll(Arrays.asList(cat1, cat4));
        p2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
        p3.getCategories().addAll(Arrays.asList(cat1, cat4));
        p4.getCategories().addAll(Arrays.asList(cat2));
        p5.getCategories().addAll(Arrays.asList(cat3));
        p6.getCategories().addAll(Arrays.asList(cat3));
        p7.getCategories().addAll(Arrays.asList(cat4));
        p8.getCategories().addAll(Arrays.asList(cat5));
        p9.getCategories().addAll(Arrays.asList(cat6));
        p10.getCategories().addAll(Arrays.asList(cat6));
        p11.getCategories().addAll(Arrays.asList(cat7));

        categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        productRepository.saveAll(Arrays.asList(p1, p2, p3));
        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

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

        String password = passwordEncoder.encode("Test@123");
        String email = "";

        if (null == userRepository.findByEmail("marks.fintech@gmail.com")) {
            email = "marks.fintech@gmail.com";
        } else {
            email = "maria2"+ System.currentTimeMillis() + "@db.com";
        }


        User u1 = new User(null, "Mary of Help", email,
                "2254879642", password, 3);

        u1.getPhones().addAll(Arrays.asList("213225566", "254136554"));

        Address ad1 = new Address(null, "Rua 40", "300", "Apt 01", "Jardins",
                "326598", u1, ct1);
        Address ad2 = new Address(null, "Rua 60", "305", "Apt 03", "Laranjeiras",
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

        OrderItem oi1 = new OrderItem(odr1, p1, 0.00, 1, 2000.0);
        OrderItem oi2 = new OrderItem(odr1, p3, 0.00, 2, 80.0);
        OrderItem oi3 = new OrderItem(odr2, p2, 100.00, 1, 800.0);

        odr1.getItems().addAll(Arrays.asList(oi1, oi2));
        odr2.getItems().addAll(Arrays.asList(oi3));

        p1.getItems().addAll(Arrays.asList(oi1));
        p1.getItems().addAll(Arrays.asList(oi3));
        p1.getItems().addAll(Arrays.asList(oi2));

        orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3));
    }

}