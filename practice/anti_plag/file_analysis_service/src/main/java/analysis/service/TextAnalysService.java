package analysis.service;

import analysis.interfaces.IFileInfoServiceMediator;
import analysis.interfaces.ITextAnalysService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TextAnalysService implements ITextAnalysService {

    private final IFileInfoServiceMediator fileInfoServiceMediator;

    public TextAnalysService(IFileInfoServiceMediator fileInfoServiceMediator) {
        this.fileInfoServiceMediator = fileInfoServiceMediator;
    }

    @Override
    public Optional<Integer> countParagraphs(int id) {

        Optional<String> optText = fileInfoServiceMediator.getFileTextById(id);
        if (optText.isEmpty()) {
            return Optional.empty();
        }
        String text = optText.get();

        int pos = 0;
        int count = 0;
        int len = text.length();

        while (pos < len) {
            int newLine = 0;
            boolean hasWords = false;

            while (pos < len && text.charAt(pos) == '\n') {
                pos += 1;
            }
            while (pos < len && text.charAt(pos) != '\n') {
                pos += 1;
                hasWords = true;
            }
            while (pos < len && text.charAt(pos) == '\n') {
                pos += 1;
                newLine += 1;
            }

            if (hasWords && (newLine > 1 || pos == len)) {
                count += 1;
            }
        }
        return Optional.of(count);
    }

    @Override
    public Optional<Integer> countWords(int id) {

        Optional<String> optText = fileInfoServiceMediator.getFileTextById(id);
        if (optText.isEmpty()) {
            return Optional.empty();
        }
        String text = optText.get();

        int pos = 0;
        int count = 0;
        int len = text.length();

        while (pos < len) {
            boolean hasSep = false;
            boolean hasWord = false;

            while (pos < len && !Character.isLetter(text.charAt(pos))) {
                pos += 1;
            }
            while (pos < len && Character.isLetter(text.charAt(pos))) {
                pos += 1;
                hasWord = true;
            }
            while (pos < len && !Character.isLetter(text.charAt(pos))) {
                pos += 1;
                hasSep = true;
            }

            if (hasWord && (hasSep || pos == len)) {
                count += 1;
            }
        }
        return Optional.of(count);
    }

    @Override
    public Optional<Integer> countSymbols(int id) {

        Optional<String> optText = fileInfoServiceMediator.getFileTextById(id);
        if (optText.isEmpty()) {
            return Optional.empty();
        }
        String text = optText.get();

        return Optional.of(text.length());
    }
}
