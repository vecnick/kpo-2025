package hse.kpo.controllers.cars;

import com.fasterxml.jackson.databind.ObjectMapper;
import hse.kpo.dto.request.CarRequest;
import hse.kpo.dto.response.CarResponse;
import hse.kpo.enums.EngineTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Создание педального автомобиля с валидными параметрами")
    void createPedalCar_ValidData_Returns201() throws Exception {
        CarRequest request = CarRequest.builder().engineType("PEDAL").pedalSize(10).build();

        String responseJson = mockMvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        CarResponse response = objectMapper.readValue(responseJson, CarResponse.class);
        assertAll(
                () -> assertNotNull(response.vin(), "VIN должен быть присвоен"),
                () -> assertEquals(EngineTypes.PEDAL.name(), response.engineType(), "Тип двигателя должен быть PEDAL")
        );
    }

    @Test
    @DisplayName("Создание ручного автомобиля с валидными параметрами")
    void createHandCar_ValidData_Returns201() throws Exception {
        CarRequest request = CarRequest.builder().engineType("HAND").build();

        String responseJson = mockMvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        CarResponse response = objectMapper.readValue(responseJson, CarResponse.class);
        assertAll(
                () -> assertNotNull(response.vin(), "VIN должен быть присвоен"),
                () -> assertEquals(EngineTypes.HAND.name(), response.engineType(), "Тип двигателя должен быть HAND")
        );
    }

    @Test
    @DisplayName("Создание летающего автомобиля с валидными параметрами")
    void createLevitationCar_ValidData_Returns201() throws Exception {
        CarRequest request = CarRequest.builder().engineType("LEVITATION").build();

        String responseJson = mockMvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        CarResponse response = objectMapper.readValue(responseJson, CarResponse.class);
        assertAll(
                () -> assertNotNull(response.vin(), "VIN должен быть присвоен"),
                () -> assertEquals(EngineTypes.LEVITATION.name(), response.engineType(),
                        "Тип двигателя должен быть LEVITATION")
        );
    }
}