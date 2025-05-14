package analysis.mediator;

import analysis.interfaces.IFileInfoServiceMediator;
import storing.interfaces.IFileInfoService;

import java.util.List;
import java.util.Optional;

public class FileInfoServiceMediator implements IFileInfoServiceMediator {
    private IFileInfoService fileInfoService;

    public FileInfoServiceMediator(IFileInfoService fileInfoService) {
        this.fileInfoService = fileInfoService;
    }

    @Override
    public void setService(IFileInfoService fileInfoService) {
        this.fileInfoService = fileInfoService;
    }

    @Override
    public Optional<List<Integer>> getAllIds() {
        try {
            return Optional.of(fileInfoService.getAllIds());
        } catch (Exception e) {
            System.out.println("Сервис FileInfoService аварийно завершился на вызове функции getAllIds.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getHashById(int id) {
        try {
            return fileInfoService.getHashById(id);
        } catch (Exception e) {
            System.out.println("Сервис FileInfoService аварийно завершился на вызове функции getHashById.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getFileTextById(int id) {
        try {
            return fileInfoService.getFileTextById(id);
        } catch (Exception e) {
            System.out.println("Сервис FileInfoService аварийно завершился на вызове функции getFileTextById.");
            return Optional.empty();
        }
    }

}
