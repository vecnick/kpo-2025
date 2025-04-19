package zooapi.zooerp2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AnimalMoveControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testMoveAnimal() throws Exception {
        String body = "{\n" +
                "  \"animalType\": \"HERBO\",\n" +
                "  \"height\": 6,\n" +
                "  \"width\": 6,\n" +
                "  \"depth\": 6,\n" +
                "  \"maxAnimalNumber\": 6\n" +
                "}";

        mockMvc.perform(post("/api/enclosures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        body = "{\n" +
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

        body = "{\n" +
                "  \"animalId\": 1,\n" +
                "  \"enclosureId\": 1\n" +
                "}";

        mockMvc.perform(post("/api/animal-move/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }
}