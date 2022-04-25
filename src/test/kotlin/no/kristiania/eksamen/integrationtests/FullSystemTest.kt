package no.kristiania.eksamen.integrationtests

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FullSystemTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGetAllAnimals(){

        val loggedInUser = mockMvc.post("/api/login"){
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"email\":\"super@admin.com\",\n" +
                    "    \"password\":\"pirate\"\n" +
                    "}"
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val adminCookie = loggedInUser.response.getCookie("access_token")

        mockMvc.get("/api/shelter/all"){
            adminCookie?.let { cookie(it) }
        }
            .andExpect { status { isOk() } }
            .andExpect { jsonPath("$"){isArray()} }
    }
}