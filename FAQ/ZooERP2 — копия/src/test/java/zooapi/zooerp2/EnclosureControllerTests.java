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
public class EnclosureControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateGetDeleteAnimal() throws Exception {

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

        body = "{}";
        mockMvc.perform(get("/api/enclosures/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/enclosures/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").doesNotExist());
    }
}
