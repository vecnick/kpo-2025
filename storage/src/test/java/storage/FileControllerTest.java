package storage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import storage.domains.FileEntity;
import storage.facades.FileFacade;
import storage.storages.FileStorage;
import storage.web.dtos.FileResponse;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FileControllerTest {
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockitoSpyBean
    private FileFacade fileFacade;

    @MockitoSpyBean
    private FileStorage fileRepository;

    @Test
    @DisplayName("Успешное добавление тестового файла")
    void uploadTest() throws Exception {
        var mockFile = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Test".getBytes());

        var mockFileEntity = new FileEntity(1, "test.txt", "path", "hash");

        doReturn(mockFileEntity).when(fileFacade).saveFile(any(MultipartFile.class));

        var response = mockMvc.perform(multipart("/api/files/upload").file(mockFile))
                              .andExpect(status().isOk())
                              .andReturn()
                              .getResponse()
                              .getContentAsString();
        objectMapper.readValue(response, FileResponse.class);
    }

    @Test
    void getTest() throws Exception {
        var mockFileEntity = new FileEntity();
        mockFileEntity.setPath("src/test/resources/data/test.txt");

        when(fileRepository.getFileById(any(Integer.class))).thenReturn(Optional.of(mockFileEntity));

        mockMvc.perform(get("/api/files/0/content"))
                              .andExpect(status().isOk())
                              .andReturn()
                              .getResponse()
                              .getContentAsString();
    }
}