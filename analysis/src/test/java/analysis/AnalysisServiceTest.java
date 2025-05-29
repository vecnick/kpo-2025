package analysis;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import analysis.repositories.StatisticRepository;
import analysis.services.AnalysisService;
import analysis.services.FileService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class AnalysisServiceTest {
    @MockitoBean
    private RestTemplate restTemplate;

    @MockitoBean
    private StatisticRepository statisticRepository;

    @Autowired
    @InjectMocks
    private FileService fileService;

    @Autowired
    @InjectMocks
    private AnalysisService analysisService;

    @BeforeEach
    public void openMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void analyzeFile_Success() {
        when(statisticRepository.getByFileId(any(Integer.class))).thenReturn(Optional.empty());
        when(statisticRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        String text = "Test text\n\nPar2\n    \nPar 3";
        doReturn(text).when(restTemplate).getForObject(any(String.class), Mockito.any());

        analysisService.analyze(0);
    }
}