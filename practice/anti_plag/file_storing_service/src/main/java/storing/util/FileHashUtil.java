package storing.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.HexFormat;

public class FileHashUtil {

    // Посчитать sha256 хэш файла в формате hex-строки
    public static String calculateSha256(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) { // открываем файл
            MessageDigest digest = MessageDigest.getInstance("SHA-256"); // создаём объект класса, вычисляющий sha256 хэш-сумму

            try (DigestInputStream dis = new DigestInputStream(inputStream, digest)) { // оборачиваем файл специальным объектом, который автоматически обновляет digest в процессе чтения файла
                byte[] buffer = new byte[4096]; // буфер 4 КБ для поблочного чтения содержимого файла
                while (dis.read(buffer) != -1) { // читаем файл пока не дойдём до конца
                    // digest обновляется автоматически
                }
            }

            byte[] hashBytes = digest.digest(); // преобразуем хэш-сумму в массив байтов

            return HexFormat.of().formatHex(hashBytes); // преобразуем байты в строку по hex формату
        } catch (Exception e) {
            throw new RuntimeException("Не удалось вычислить SHA-256 хэш файла", e);
        }
    }

}
