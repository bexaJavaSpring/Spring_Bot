package com.example.spring_bot.bot;

import com.example.spring_bot.entity.User;
import com.example.spring_bot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ShamsBot extends TelegramLongPollingBot {
   final UserRepository userRepository;
  final TelegramService telegramService;

    @Value("${telegram_bot_botToken}")
    private String token;

    @Value("${telegram_bot_username}")
    private String username;

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
      return token;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        User currentUser;

        if (update.hasMessage()) {
            Optional<User> optionalUser = userRepository.findByChatId(String.valueOf(update.getMessage().getChatId()));
            Message message = update.getMessage();
            if (message.hasText()) {
                if (message.getText().equals("/start")) {
                    if (optionalUser.isPresent()) {
                        currentUser = optionalUser.get();
                        currentUser.setState(BotState.START);
                        currentUser.setFullName(update.getMessage().getFrom().getFirstName());
                        userRepository.save(currentUser);
                    } else {
                        currentUser = new User();
                        currentUser.setChatId(String.valueOf(update.getMessage().getChatId()));
                        currentUser.setState(BotState.START);
                        userRepository.save(currentUser);
                    }
                    execute(telegramService.welcome(update));
                } else {
                    currentUser = optionalUser.get(); //qaysi bosqichda
                    switch (currentUser.getState()) {
                        case BotState.START:
                            //menyudan 5 xil button bosishi mn
                            switch (update.getMessage().getText()) {
                                case Constants.ORDER_BUTTON:
                                    currentUser.setState(BotState.ORDER);
                                    userRepository.save(currentUser);
                                    execute(telegramService.order(update));
                                    break;
                                case Constants.MY_ORDERS_BUTTON:
                                    execute(telegramService.myOrders(update));
                                    break;
                                case Constants.SETTINGS:
                                    execute(telegramService.settings(update));
                                    break;
                                case Constants.ABOUT_US:
                                    execute(telegramService.aboutUs(update));
                                    break;
                                case Constants.LEAVE_COMMENT:
                                    execute(telegramService.comment(update));
                                    break;
                            }
                            break;
                        case BotState.ORDER:
                            switch (update.getMessage().getText()) {
                                case Constants.BACK:
                                case Constants.MAIN_MENU_BUTTON:
                                    currentUser.setState(BotState.START);
                                    userRepository.save(currentUser);
                                    execute(telegramService.welcome(update));
                                    break;
                                default:
                                    currentUser.setState(BotState.SELECT_CAT);
                                    userRepository.save(currentUser);
                                    execute(telegramService.selectCategory(update));
                            }
                            break;
                        case BotState.SELECT_CAT:
                            switch (update.getMessage().getText()) {
                                case Constants.MAIN_MENU_BUTTON:
                                    currentUser.setState(BotState.START);
                                    userRepository.save(currentUser);
                                    execute(telegramService.welcome(update));
                                    break;
                                case Constants.BACK:
                                    currentUser.setState(BotState.ORDER);
                                    userRepository.save(currentUser);
                                    execute(telegramService.order(update));
                                    break;
                                default:
                                    currentUser.setState(BotState.SELECT_PRO);
                                    userRepository.save(currentUser);
                                    execute(telegramService.selectProduct(update));
                                    execute(telegramService.sendMessageWithProduct(update));
                            }
                            break;
                    }
                }
            }
        }
    }
}
