package hse.kpo.service;

import static java.lang.Integer.parseInt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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

        IntSummaryStatistics legStats = customers.stream()
            .filter(CustomerData::isValid)
            .mapToInt(CustomerData::legPower)
            .summaryStatistics();

        IntSummaryStatistics handStats = customers.stream()
            .filter(CustomerData::isValid)
            .mapToInt(CustomerData::handPower)
            .summaryStatistics();

        IntSummaryStatistics iqStats = customers.stream()
            .filter(CustomerData::isValid)
            .mapToInt(CustomerData::iq)
            .summaryStatistics();

        long totalCars = customers.stream()
            .filter(CustomerData::isValid)
            .mapToInt(CustomerData::carsCount)
            .sum();

        long totalCatamarans = customers.stream()
            .filter(CustomerData::isValid)
            .mapToInt(CustomerData::catamaransCount)
            .sum();

        long totalTransport = totalCars +totalCatamarans;

        List<CustomerData> suspicious = customers.stream()
            .filter(c -> c.legPower() > 1000 || c.handPower() > 1000 || c.iq() > 300)
            .toList();

        var topByTransport = customers.stream().sorted(Comparator.comparing(CustomerData::carsCount).reversed()).toList();

        // Формируем сообщение
        StringBuilder message = new StringBuilder()
            .append("🏪 *Детальный отчет о продажах*\n")
            .append(String.format("📅 %s%n%n", LocalDate.now()))

            // Основная статистика
            .append("📊 *Основные показатели:*\n")
            .append(String.format("💰 Всего продаж: %d%n", totalSales))
            .append(String.format("👤 Уникальных клиентов: %d%n", customerMap.size()))
            .append(String.format("🚘 Всего транспорта: %d%n", totalTransport))
            .append(String.format("✅ Корректные записи: %d%n", validCount))
            .append(String.format("⚠️ Некорректные записи: %d%n%n", invalidCount))

            // Физические показатели
            .append("🏋️ *Физические характеристики:*\n")
            .append(String.format("🦵 Сила ног: Ø%.1f (min: %d, max: %d)%n",
                legStats.getAverage(), legStats.getMin(), legStats.getMax()))
            .append(String.format("💪 Сила рук: Ø%.1f (min: %d, max: %d)%n",
                handStats.getAverage(), handStats.getMin(), handStats.getMax()))
            .append(String.format("🧠 IQ: Ø%.1f (min: %d, max: %d)%n%n",
                iqStats.getAverage(), iqStats.getMin(), iqStats.getMax()))

            // Распределение транспорта
            .append("🚘 *Распределение транспорта:*\n")
            .append(String.format("🚗 Автомобили: %d (%.1f%%)%n",
                totalCars, (totalCars * 100.0) / totalTransport))
            .append(String.format("🚤 Катамараны: %d (%.1f%%)%n%n",
                totalCatamarans, (totalCatamarans * 100.0) / totalTransport))

            // Топы
            .append("🏆 *Топ клиентов:*\n")
            .append("🥇 Лучший покупатель: ")
            .append(!topByTransport.isEmpty()
                ? topByTransport.getFirst().name()
                : "No customers")
            .append("\n")
            .append("🔝 Топ-3 по авто:\n")
            .append(topByTransport.stream()
                .limit(3)
                .map(c -> String.format("▫ %s: %d авто", c.name(), c.carsCount()))
                .collect(Collectors.joining("\n")))
            .append("\n\n")

            // Аномалии
            .append("🚨 *Подозрительные записи:*\n")
            .append(suspicious.isEmpty() ? "ℹ️ Нет аномалий" :
                suspicious.stream()
                    .map(c -> String.format(
                        "▫ %s (🚩Ноги:%d 🚩Руки:%d 🚩IQ:%d)",
                        c.name(), c.legPower(), c.handPower(), c.iq()))
                    .collect(Collectors.joining("\n")))
            .append("\n\n")

            // Заключение
            .append("📈 *Эффективность продаж:*\n")
            .append(String.format("📦 Продаж/клиент: Ø%.1f",
                (double) totalSales / customerMap.size()));

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