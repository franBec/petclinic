package dev.pollito.petclinic_java_gradle_react_tailwind_ts;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.config.BaseIntegrationSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class LoginSpec extends BaseIntegrationSpec {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin123";

    @BeforeEach
    void setUp() {
        initMockMvc();
        createTestUser(USERNAME, PASSWORD);
    }

    @Test
    void shouldRejectUnauthenticatedApiRequest() throws Exception {
        mockMvc.perform(get("/api/owners"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnJwtOnValidLogin() throws Exception {
        String body = mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"username":"%s","password":"%s"}
                        """.formatted(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isString())
                .andReturn().getResponse().getContentAsString();

        String jwt = JsonPath.read(body, "$.data");
        org.assertj.core.api.Assertions.assertThat(jwt).isNotBlank();
    }

    @Test
    void shouldRejectInvalidCredentials() throws Exception {
        mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"username":"%s","password":"wrongpassword"}
                        """.formatted(USERNAME)))
                .andExpect(status().isUnauthorized());
    }
}
