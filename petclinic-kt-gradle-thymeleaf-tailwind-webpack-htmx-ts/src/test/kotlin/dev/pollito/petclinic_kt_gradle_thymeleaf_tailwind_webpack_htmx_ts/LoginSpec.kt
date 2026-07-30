package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.config.BaseIntegrationSpec
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class LoginSpec : BaseIntegrationSpec() {

    companion object {
        private const val USERNAME = "admin"
        private const val PASSWORD = "admin123"
    }

    @BeforeEach
    fun setUp() {
        initMockMvc()
        createTestUser(USERNAME, PASSWORD)
    }

    @Test
    fun shouldRedirectToLoginWhenAccessingRoot() {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
    }

    @Test
    fun shouldLoginWithValidCredentials() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/login")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("username", USERNAME)
                .param("password", PASSWORD),
        )
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
    }

    @Test
    fun shouldFailLoginWithInvalidCredentials() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/login")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("username", USERNAME)
                .param("password", "wrongpassword"),
        )
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
    }
}
