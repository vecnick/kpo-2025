package storage;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import storage.domains.FileEntity;
import storage.facades.FileFacade;
import storage.storages.FileStorage;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class fileFacadeTest {
    ObjectMapper objectMapper = new ObjectMapper();

    @MockitoSpyBean
    FileStorage fileRepository;

    @MockitoSpyBean
    private FileFacade fileFacade;

    @Test
    void saveTest() throws Exception {
        var mockFile = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Test".getBytes());

        var fileEntity = fileFacade.saveFile(mockFile);

        assertAll(
                () -> assertNotNull(fileEntity.getId()),
                () -> assertEquals(fileEntity.getHash(), fileFacade.getFileHash(fileEntity.getId())),
                () -> assertEquals(fileEntity.getName(), mockFile.getOriginalFilename())
        );
    }

    @Test
    void saveFailTest() throws Exception {
        var mockFile = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Test".getBytes());
        var oldFilesCount = fileRepository.findAll().size();

        Assertions.assertThrows(IOException.class, () -> {fileFacade.saveFile(mockFile);});

        assertEquals(oldFilesCount, fileRepository.findAll().size());
    }

    @Test
    void loadTest() throws Exception {
        var testEntity = new FileEntity();
        testEntity.setPath("src/test/resources/data/test.txt");

        Mockito.when(fileRepository.getFileById(any(Integer.class))).thenReturn(Optional.of(testEntity));

        var data = fileFacade.getContentById(0);

        assertEquals("Test", data);
    }
}