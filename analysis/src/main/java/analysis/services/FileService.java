package analysis.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class FileService {
    RestTemplate restTemplate;

    public String getFileContent(Integer fileId) {
        String url = "http://storage:8081/api/files/" + fileId + "/content";

        return restTemplate.getForObject(url, String.class);
    }

    public Integer getSameCount(Integer fileId) {
        String url = "http://storage:8081/api/files/" + fileId + "/same";

        return restTemplate.getForObject(url, Integer.class);
    }
}