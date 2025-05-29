package analysis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import analysis.repositories.StatisticRepository;
import analysis.services.AnalysisService;
import analysis.services.FileService;
import analysis.web.dtos.StatisticResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AnalysisControllerTest {
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockitoSpyBean
    private FileService fileService;
    @MockitoSpyBean
    private StatisticRepository statisticRepository;
    @MockitoSpyBean
    private AnalysisService analysisService;

    @Test
    void analTest() throws Exception {
        var content = "bla bla\nbla";

        doReturn(content).when(fileService).getFileContent(any(Integer.class));
        doReturn(Optional.empty()).when(statisticRepository).getByFileId(any(Integer.class));
        doAnswer(i -> i.getArgument(0)).when(statisticRepository).save(any());

        var response = mockMvc.perform(get("/api/analysis/1"))
                              .andExpect(status().isOk())
                              .andReturn()
                              .getResponse()
                              .getContentAsString();
        objectMapper.readValue(response, StatisticResponse.class);
    }

    @Test

    void plagiatTest() throws Exception {
        doReturn(1).when(fileService).getSameCount(any(Integer.class));
        var response = mockMvc.perform(get("/api/analysis/1/plagiarised"))
                              .andExpect(status().isOk())
                              .andReturn()
                              .getResponse()
                              .getContentAsString();
        var resp = objectMapper.readValue(response, Boolean.class);
        assertEquals(false, resp);
    }
}