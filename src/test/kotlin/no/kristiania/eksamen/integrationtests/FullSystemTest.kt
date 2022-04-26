package no.kristiania.eksamen.integrationtests

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import no.kristiania.eksamen.testdata.DummyData
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FullSystemTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val adminLogin = "{\n" +
            "    \"email\":\"super@admin.com\",\n" +
            "    \"password\":\"pirate\"\n" +
            "}"

    private val employeeLogin = "{\n" +
            "    \"email\":\"ordinary@employee.com\",\n" +
            "    \"password\":\"sailor\"\n" +
            "}"

    private val userLogin = "{\n" +
            "    \"email\":\"normal@user.com\",\n" +
            "    \"password\":\"viking\"\n" +
            "}"

    private val userRegister = "{\n" +
            "    \"email\":\"please@work.com\",\n" +
            "    \"password\":\"no\"\n" +
            "}"

    @Test
    fun adminShouldAddEmployerTest() {

        val loggedInUser = mockMvc.post("/api/login") {
            contentType = MediaType.APPLICATION_JSON
            content = adminLogin
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val cookie = loggedInUser.response.getCookie("access_token")

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/authority/employee")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(DummyData().getTestUserDtoList()[0]))
                .characterEncoding("utf-8")
        )
            .andExpect { status().isOk }
            .andReturn()

    }

    @Test
    fun employeeShouldNotAddEmployerTest() {

        val loggedInUser = mockMvc.post("/api/login") {
            contentType = MediaType.APPLICATION_JSON
            content = employeeLogin
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val cookie = loggedInUser.response.getCookie("access_token")

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/authority/employee")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(DummyData().getTestUserDtoList()[0]))
                .characterEncoding("utf-8")
        )
            .andExpect { status().isUnauthorized }
            .andReturn()
    }

    @Test
    fun userShouldSeeAnimals() {

        val loggedInUser = mockMvc.post("/api/login") {
            contentType = MediaType.APPLICATION_JSON
            content = userLogin
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val cookie = loggedInUser.response.getCookie("access_token")

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/shelter/")
                .cookie(cookie)
        )
            .andExpect { status().isOk }
            .andReturn()
    }

    @Test
    fun userShouldNotPostToShelter() {

        val loggedInUser = mockMvc.post("/api/login") {
            contentType = MediaType.APPLICATION_JSON
            content = userLogin
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val cookie = loggedInUser.response.getCookie("access_token")

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/shelter/")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(DummyData().getTestAnimalDtoList()[0]))
                .characterEncoding("utf-8")
        )
            .andExpect { status().isUnauthorized }
            .andReturn()
    }

    @Test
    fun nonLoggedInShouldRegister() {

        val loggedInUser = mockMvc.post("/api/register") {
            contentType = MediaType.APPLICATION_JSON
            content = userRegister
        }
            .andExpect { status { isOk() } }
            .andReturn()
    }
}


