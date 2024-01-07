package com.example.expand_apis_task;

import com.example.expand_apis_task.model.dto.base.ResponseDTOFactory;
import com.example.expand_apis_task.model.dto.UserDTO;
import com.example.expand_apis_task.model.entity.RoleEntity;
import com.example.expand_apis_task.repository.RoleRepository;
import com.example.expand_apis_task.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@AutoConfigureMockMvc(addFilters = false)
class UserControllerIntegrationTest {

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

    public final String USERNAME = "Mykola";
    public final String PASSWORD = "petrovych666";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
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
        userRepository.deleteAll();
    }

    @Test
    void userAdd_ok() throws Exception {
        var content = objectMapper.writeValueAsString(UserDTO.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build());
        var expectedResponse = objectMapper.writeValueAsString(ResponseDTOFactory.getOkResponseDto("User added successfully"));

        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void userAdd_alreadyExist() throws Exception {
        var content = objectMapper.writeValueAsString(UserDTO.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build());
        var expectedResponse = objectMapper.writeValueAsString(ResponseDTOFactory.getOkResponseDto("User added successfully"));

        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void userAdd_wrongContent() throws Exception {
        var wrongContent = objectMapper.writeValueAsString(new UserDTO());

        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wrongContent))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value("UserAddException handled"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("FAILED"));
    }

    @Test
    void userAdd_noContent() throws Exception {
        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value("HttpMessageNotReadableException handled"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("FAILED"));
    }

    @Test
    void userAuthenticate_ok() throws Exception {
        var content = objectMapper.writeValueAsString(UserDTO.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build());

        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("User added successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"));

        mockMvc.perform(post("/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"));
    }

    @Test
    void userAuthenticate_wrongContent() throws Exception {
        String content = objectMapper.writeValueAsString(UserDTO.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build());

        mockMvc.perform(post("/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is(401))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value("UserAuthException handled"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("FAILED"));
    }
}
