package org.example.integration;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
/*
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StudentApplication.class, StudentConfig.class})
@WebAppConfiguration(value = "")
@WebMvcTest(UserController.class)
public class IntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        mockMvc.perform(get("/private/hello").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DirtiesContext
    public void givenGreetURIWithPathVariable_whenMockMVC_thenResponseOK() throws Exception {

        UserEntity user = new UserEntity(2, "1234321234321", "John", "Doe", "mail", "Pass2");
        this.mockMvc
                .perform(get("/user/{id}", 2))
                .andDo(print()).andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType("application/json;charset=UTF-8"))
                .andExpect((ResultMatcher) jsonPath("$.firstName").value(""));
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertInstanceOf(MockServletContext.class, servletContext);
        assertNotNull(webApplicationContext.getBean("userController"));
    }
}

 */