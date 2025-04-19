# Занятие 13. Межсервисное взаимодействие

## Цель занятия
- Научиться связывать приложения с помощью grpc.
## Требования к реализации
1) Добавить новый сервис в папке practise/notifications, который будет отправлять отчет в @tg
   - Связь с основным должна быть по grpc
   - Отчет должен отправляться в @tg
## Тестирование
- При старте основного проекта и продажи там машины, поднятый сервис уведомлений должен кидать отчет в тг
## Задание на доработку
## Пояснения к реализации
application.yaml
```
grpc:
  client:
    report-service:
      address: 'static://localhost:9090' # Порт grpc основного приложения
      negotiation-type: plaintext

telegram:
  bot:
    username: ${USERNAME}
    token: ${TELEGRAM_BOT_TOKEN}
    chat-id: ${TELEGRAM_CHAT_ID}
```
В build.gradle добавьте все нужные зависимости из основного проекта, не забудьте про grpc, а так же tg:
```
    // Telegram
    implementation("org.telegram:telegrambots-spring-boot-starter:6.9.7.1")
    
    // gRPC
    implementation("io.grpc:grpc-stub:1.62.2")
    implementation("io.grpc:grpc-protobuf:1.62.2")
    implementation("net.devh:grpc-client-spring-boot-starter:3.0.0.RELEASE")
    compileOnly("org.apache.tomcat:annotations-api:6.0.53")
```
Скопируйте файл proto из основного проекта.
Далее нужны сервис для работы с тг:
```
package hse.kpo.tg;

import hse.kpo.config.TelegramConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class NotificationBot extends TelegramLongPollingBot {
    private final TelegramConfig config;

    public NotificationBot(TelegramConfig config) {
        super(config.getToken());
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Логика обработки входящих сообщений
    }

    public String getChatId() {
        return config.getChatId();
    }
}
```
Добавьте конфиги 
```
package hse.kpo.config;

import hse.kpo.grpc.ReportServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {

    @GrpcClient("report-service")
    private ReportServiceGrpc.ReportServiceBlockingStub reportServiceStub;

    @Bean
    public ReportServiceGrpc.ReportServiceBlockingStub reportServiceStub() {
        return reportServiceStub;
    }
}
```
и 
```
package hse.kpo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@ConfigurationProperties(prefix = "telegram.bot")
@Getter
@Setter
public class TelegramConfig {
    private String username;
    private String token;
    private String chatId;

    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        return new TelegramBotsApi(DefaultBotSession.class);
    }
}
```
 Нужно создать внутреннюю сущность для обработки отчета
```
package hse.kpo.service;

public record CustomerData(
    String name,
    int legPower,
    int handPower,
    int iq,
    int carsCount,
    int catamaransCount,
    boolean isValid
) {
    public static final CustomerData INVALID = new CustomerData(
        null, 0, 0, 0, 0, 0, false
    );

    public static CustomerData createValid(String name, int legPower,
                                           int handPower, int iq,
                                           int cars, int catamarans) {
        return new CustomerData(
            name,
            legPower,
            handPower,
            iq,
            cars,
            catamarans,
            name != null &&
                legPower > 0 && legPower < 1000 &&
                handPower > 0 && handPower < 1000 &&
                iq > 0 && iq < 300
        );
    }
}

```
И сервис, который будет получать данные из основного приложения, парсить отчет и скидывать в тг
```
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
```
<details> 
<summary>Ссылки</summary>
1. 
</details>