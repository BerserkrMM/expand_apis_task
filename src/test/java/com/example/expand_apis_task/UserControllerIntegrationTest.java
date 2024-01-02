package com.example.expand_apis_task;

import com.example.expand_apis_task.config.JwtGenerator;
import com.example.expand_apis_task.dto.UserDTO;
import com.example.expand_apis_task.model.Role;
import com.example.expand_apis_task.repository.RoleRepository;
import com.example.expand_apis_task.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0");

    static {
        var l = new ArrayList<String>();
        l.add("55191:3306");
        mySQLContainer.setPortBindings(l);
    }

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", mySQLContainer::getDriverClassName);
    }

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtGenerator jwtGenerator;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void init() {
        if (roleRepository.findAll().size() != 3) {
            roleRepository.deleteAll();
            Role r1 = new Role();
            Role r2 = new Role();
            Role r3 = new Role();
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
        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {                             "
                                + "\"username\": \"username666\","
                                + "\"password\" : \"password666\""
                                + "}                             "))
                .andExpect(status().is(200))
                .andExpect(content().string("User added successfully."));
    }

    @Test
    void userAdd_wrongContent() throws Exception {
        try {
            mockMvc.perform(post("/user/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(" {                      "
                            + "\"wrongField\": \"1\", "
                            + "\"wrongField2\" : \"2\""
                            + "}                      "));
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getCause().getClass());
            assertEquals("Password cannot be null", e.getCause().getMessage());
        }
    }

    @Test
    void userAdd_noContent() throws Exception {
        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().is(400));
    }

    @Test
    void userAuthenticate_ok() throws Exception {

        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {                             "
                                + "\"username\": \"Mykola\","
                                + "\"password\" : \"petrovych666\"   "
                                + "}                             "))
                .andExpect(content().string("User added successfully."));

        UserDTO userDTO = UserDTO.builder()
                .username("Mykola")
                .password("petrovych666")
                .build();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getUsername(),
                        userDTO.getPassword()));
        final String token = jwtGenerator.generateToken(authentication);

        mockMvc.perform(post("/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {                             "
                                + "\"username\": \"Mykola\","
                                + "\"password\" : \"petrovych666\"   "
                                + "}                             "))
                .andExpect(status().is(200))
                .andExpect(content().string(token));
    }

    @Test
    void userAuthenticate_noSuchUser() throws Exception {

        mockMvc.perform(post("/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {                             "
                                + "\"username\": \"MAkola\","
                                + "\"password\" : \"petrovych666\"   "
                                + "}                             "))
                .andExpect(status().is(404))
                .andExpect(content().string("No such User registered!"));
    }

    @Test
    void userAuthenticate_wrongPassword() throws Exception {

        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {                             "
                                + "\"username\": \"Mykola\","
                                + "\"password\" : \"petrovych666\"   "
                                + "}                             "))
                .andExpect(content().string("User added successfully."));

        mockMvc.perform(post("/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {                             "
                                + "\"username\": \"Mykola\","
                                + "\"password\" : \"petrovych777\"   "
                                + "}                             "))
                .andExpect(status().is(401))
                .andExpect(r -> assertEquals("Bad credentials", r.getResponse().getErrorMessage()));
    }

}
