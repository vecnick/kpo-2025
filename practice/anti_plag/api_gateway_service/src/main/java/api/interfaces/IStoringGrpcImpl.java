package api.interfaces;

import api.record.FileInfoParams;
import storing.FileInfoOuterClass;

import java.util.List;
import java.util.Optional;

public interface IStoringGrpcImpl {

    Optional<List<FileInfoParams>> getAll();
    Optional<FileInfoParams> getById(int id);
    Optional<String> getLocationById(int id);
    Optional<String> getFileTextById(int id);

    Optional<Integer> saveFileInfo(String location, String text);
    boolean deleteFileInfoById(int id);
}
