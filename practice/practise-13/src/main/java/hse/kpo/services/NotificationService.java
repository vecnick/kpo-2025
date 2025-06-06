package hse.kpo.services;

import static java.lang.Integer.parseInt;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import hse.kpo.grpc.ReportResponse;
import hse.kpo.grpc.ReportServiceGrpc;
import hse.kpo.tg.NotificationBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final ReportServiceGrpc.ReportServiceBlockingStub reportService;
    private final NotificationBot notificationBot;

    @Scheduled(fixedRate = 60_000)  // Каждую минуту
    public void checkSalesAndNotify() {
        log.warn("getting report");
        ReportResponse report = reportService.getLatestReport(null);
        parseAndSendNotifications(report.getContent());
    }

    private void parseAndSendNotifications(String reportContent) {
        // Отделяем секцию с покупателями от операций
        String[] parts = reportContent.split("Операция: ");
        String customersSection = parts[0];

        // Извлекаем всех покупателей
        List<CustomerData> customers = new ArrayList<>();
        Pattern customerPattern = Pattern.compile("Customer\\{([^}]+)\\}");
        Matcher matcher = customerPattern.matcher(customersSection);

        while (matcher.find()) {
            String customerData = matcher.group(1);
            customers.add(parseCustomer(customerData));
        }

        // Парсим операции продаж
        int totalSales = parts.length - 1; // Первая часть - не операция

        // Формируем статистику
        Map<String, CustomerData> customerMap = new LinkedHashMap<>();
        int validCount = 0, invalidCount = 0;

        for (CustomerData customer : customers) {
            if (customer.isValid()) {
                customerMap.merge(customer.name(), customer, (old, neu) -> neu);
                validCount++;
            } else {
                invalidCount++;
            }
        }

        // Формируем сообщение
        StringBuilder message = new StringBuilder()
                .append("🏪 *Отчет о продажах*\n")
                .append(String.format("📅 %s%n%n", LocalDate.now()))
                .append(String.format("💰 Всего продаж: %d%n", totalSales))
                .append(String.format("👤 Уникальных клиентов: %d%n", customerMap.size()))
                .append(String.format("✅ Корректные записи: %d%n", validCount))
                .append(String.format("⚠️ Некорректные записи: %d%n%n", invalidCount))
                .append("🚘 *Статистика транспорта:*\n");

        sendToTelegram(message.toString());
    }

    private CustomerData parseCustomer(String raw) {
        try {
            String name = extractValue(raw, "name='([^']+)");
            int legPower = parseInt(extractValue(raw, "legPower=([\\d-]+)"));
            int handPower = parseInt(extractValue(raw, "handPower=([\\d-]+)"));
            int iq = parseInt(extractValue(raw, "iq=([\\d-]+)"));

            // Парсим автомобили
            String carsStr = extractValue(raw, "cars=\\[([^]]+)]");
            int carsCount = carsStr == null ? 0 : carsStr.split(",").length;

            // Парсим катамараны
            boolean hasCatamaran = !raw.contains("catamaran=null");

            return CustomerData.createValid(
                    name,
                    legPower,
                    handPower,
                    iq,
                    carsCount,
                    hasCatamaran ? 1 : 0
            );
        } catch (Exception e) {
            return CustomerData.INVALID;
        }
    }

    private void sendToTelegram(String message) {
        log.warn("sending to tg");
        SendMessage sendMessage = new SendMessage(
                notificationBot.getChatId(),
                message
        );
        try {
            notificationBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String extractValue(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? matcher.group(1).trim() : null;
    }
}