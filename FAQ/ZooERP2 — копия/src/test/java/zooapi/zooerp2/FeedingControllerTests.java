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
public class FeedingControllerTests {
    @Autowired
    private MockMvc mockMvc;



    @Test
    void testCreateGetDeleteFeeding() throws Exception {
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

        body = "{\n" +
                "  \"animalId\": 1,\n" +
                "  \"startTime\": \"2020-04-15\",\n" +
                "  \"delta\": \"PT59M\",\n" +
                "  \"foodType\": \"HERB\"\n" +
                "}";

        mockMvc.perform(post("/api/feeding")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        mockMvc.perform(get("/api/feeding")
                        .param("startDate", "2020-04-15T00:00:00")
                        .param("endDate", "2020-04-16T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
        body = "{}";
        mockMvc.perform(post("/api/feeding/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(delete("/api/feeding/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").doesNotExist());
    }
}
