package com.example.expand_apis_task;

import com.example.expand_apis_task.model.dto.AddProductsDTO;
import com.example.expand_apis_task.model.dto.ProductDTO;
import com.example.expand_apis_task.model.entity.RoleEntity;
import com.example.expand_apis_task.repository.RoleRepository;
import com.example.expand_apis_task.repository.UserRepository;
import com.example.expand_apis_task.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerIntegrationTest {

    public static final Logger LOG = LoggerFactory.getLogger("Integration test");

    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0");

    static {
        mySQLContainer.setPortBindings(List.of("55191:3306"));
    }

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", mySQLContainer::getDriverClassName);
    }

    private final ProductDTO product_1 = ProductDTO.builder()
            .id(1L)
            .entryDate("03-01-2023")
            .itemCode("12674")
            .itemName("T")
            .itemQuantity(206L)
            .status("Paid")
            .build();

    private final ProductDTO product_2 = ProductDTO.builder()
            .id(2L)
            .entryDate("03-01-2023")
            .itemCode("900064")
            .itemName("M")
            .itemQuantity(250L)
            .status("Paid")
            .build();

    private final String TABLE_NAME = "products";

    @Autowired
    private EntityManagerFactory emf;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void init() {
        var em = emf.createEntityManager();
        var tr = em.getTransaction();
        try {
            tr.begin();
            em.createNativeQuery(
                            "        CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (     "
                                    + "    id            BIGINT AUTO_INCREMENT NOT NULL,    "
                                    + "    entry_date    VARCHAR(255)          NULL,        "
                                    + "    item_code     VARCHAR(255)          NOT NULL,    "
                                    + "    item_name     VARCHAR(255)          NULL,        "
                                    + "    item_quantity INT                   NULL,        "
                                    + "    status        VARCHAR(255)          NULL,        "
                                    + "    CONSTRAINT pk_" + TABLE_NAME + " PRIMARY KEY (id)"
                                    + ");                                                   "
                    )
                    .executeUpdate();
            tr.commit();
            LOG.debug("Table '" + TABLE_NAME + "' created.");
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
                LOG.error("Table '" + TABLE_NAME + "' not created! Transaction rolled back. Exception: ");
            }
            throw e;
        }
        if (roleRepository.findAll().size() != 3) {
            roleRepository.deleteAll();
            RoleEntity r1 = new RoleEntity();
            RoleEntity r2 = new RoleEntity();
            RoleEntity r3 = new RoleEntity();
            r1.setName("ROLE_USER");
            r2.setName("ROLE_ADMIN");
            r3.setName("ROLE_GUEST");
            roleRepository.saveAll(List.of(r1, r2, r3));
        }

    }

    @AfterEach
    void clear() {
        var em = emf.createEntityManager();
        var tr = em.getTransaction();
        try {
            tr.begin();
            em.createNativeQuery("DELETE FROM " + TABLE_NAME).executeUpdate();
            userRepository.deleteAll();
            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
                LOG.error("Cannot delete from '" + TABLE_NAME + "' table. Exception: ");
            }
            throw e;

        }
    }

    @Test
    void productAdd_ok() throws Exception {
        var content = objectMapper.writeValueAsString(new AddProductsDTO(TABLE_NAME, List.of(product_1, product_2)));

        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data").value("Records added successfully"))
                .andExpect(jsonPath("$.status").value("OK"));
    }

    @Test
    void productAdd_wrongContent_tableIsNull() throws Exception {
        var products = new AddProductsDTO(null, List.of(product_1, product_2));
        var content = objectMapper.writeValueAsString(products);


        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("TableCreationException handled"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.status").value("FAILED"));
    }

    @Test
    void productAdd_wrongContent_wrongProduct() throws Exception {
        var products = new AddProductsDTO(TABLE_NAME, List.of(new ProductDTO(), product_2));
        var content = objectMapper.writeValueAsString(products);


        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("ProductAddException handled"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.status").value("FAILED"));
    }

    @Test
    void productAll_ok() throws Exception {
        var content = objectMapper.writeValueAsString(new AddProductsDTO(TABLE_NAME, List.of(product_1, product_2)));

        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data").value("Records added successfully"))
                .andExpect(jsonPath("$.status").value("OK"));

        var savedProducts = productService.getAll();
        product_1.setId(savedProducts.get(0).getId());
        product_2.setId(savedProducts.get(1).getId());

        mockMvc.perform(get("/products/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data[0]").value(product_1))
                .andExpect(jsonPath("$.data[1]").value(product_2))
                .andExpect(jsonPath("$.status").value("OK"));
    }
}
