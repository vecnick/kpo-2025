package zooapi.zooerp2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AnimalControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateGetDeleteAnimal() throws Exception {

        String body = "{\n" +
                "  \"animalType\": \"HERBO\",\n" +
                "  \"name\": \"Rex\",\n" +
                "  \"sex\": \"MALE\",\n" +
                "  \"foodType\": \"HERB\",\n" +
                "  \"birthday\": \"2020-04-15\"\n" +
                "}";

        mockMvc.perform(post("/api/animals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id").exists());

        body = "{}";
        mockMvc.perform(get("/api/animals/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/animals/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").doesNotExist());
    }
}
