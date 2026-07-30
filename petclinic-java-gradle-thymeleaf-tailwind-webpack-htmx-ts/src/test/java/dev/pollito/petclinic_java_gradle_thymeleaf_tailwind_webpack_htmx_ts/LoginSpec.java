package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.config.BaseIntegrationSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginSpec extends BaseIntegrationSpec {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin123";

    @BeforeEach
    void setUp() {
        initMockMvc();
        createTestUser(USERNAME, PASSWORD);
    }

    @Test
    void shouldRedirectToLoginWhenAccessingRoot() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void shouldLoginWithValidCredentials() throws Exception {
        mockMvc.perform(post("/login")
                .with(csrf())
                .param("username", USERNAME)
                .param("password", PASSWORD))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void shouldFailLoginWithInvalidCredentials() throws Exception {
        mockMvc.perform(post("/login")
                .with(csrf())
                .param("username", USERNAME)
                .param("password", "wrongpassword"))
                .andExpect(status().is3xxRedirection());
    }
}
