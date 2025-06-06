package org.example.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FilesMappingService {
    private Map<String, String> filesMapping = new HashMap<>();

    public void addFile(String fileName, String hash) {
        if (filesMapping.containsKey(fileName)) {
            throw new RuntimeException("file with name" + fileName + " already exists");
        } else {
            filesMapping.put(fileName, hash);
        }
    }

    public String getHashByName(String fileName) {
        if (filesMapping.containsKey(fileName)) {
            return filesMapping.get(fileName);
        }
        throw new RuntimeException("no file with name " + fileName);
    }
}
