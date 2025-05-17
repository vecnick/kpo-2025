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