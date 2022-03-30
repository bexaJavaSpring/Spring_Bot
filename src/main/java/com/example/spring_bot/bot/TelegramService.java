package com.example.spring_bot.bot;

import com.example.spring_bot.entity.Attachment;
import com.example.spring_bot.entity.AttachmentContent;
import com.example.spring_bot.entity.Book;
import com.example.spring_bot.entity.Category;
import com.example.spring_bot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TelegramService implements TelegramServiceInterface {
    static String chatId = null;
    final UserRepository userRepository;
    final CategoryRepository categoryRepository;
    final BookRepository bookRepository;
    final AttachmentRepository attachmentRepository;
    final AttachmentContentRepository attachmentContentRepository;
    @Override
    public SendMessage welcome(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText("Salom " + update.getMessage().getFrom().getFirstName() + " " + (update.getMessage().getFrom().getLastName() == null ? "" : update.getMessage().getFrom().getLastName()));

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        KeyboardButton button = new KeyboardButton(Constants.ORDER_BUTTON);
        KeyboardButton button1 = new KeyboardButton(Constants.MY_ORDERS_BUTTON);
        KeyboardButton button2 = new KeyboardButton(Constants.SETTINGS);
        KeyboardButton button3 = new KeyboardButton(Constants.ABOUT_US);
        KeyboardButton button4 = new KeyboardButton(Constants.LEAVE_COMMENT);
        row.add(button);
        row1.add(button1);
        row1.add(button2);
        row2.add(button3);
        row2.add(button4);
        keyboardRowList.add(row);
        keyboardRowList.add(row1);
        keyboardRowList.add(row2);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    @Override
    public SendMessage order(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(Constants.BEGIN);


        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();


        List<Category> all = categoryRepository.findAll();
        int size = all.size();
        int r = (size % 2 == 1) ? size - 1 : size;
        for (int i = 0; i < r; i = i + 2) {
            KeyboardRow row1 = new KeyboardRow();
            KeyboardButton button = new KeyboardButton(all.get(i).getName());
            KeyboardButton button1 = new KeyboardButton(all.get(i + 1).getName());
            row1.add(button);
            row1.add(button1);
            keyboardRowList.add(row1);
        }
        if (size % 2 == 1) {
            KeyboardRow row = new KeyboardRow();
            KeyboardButton keyboardButton = new KeyboardButton();
            keyboardButton.setText(all.get(size - 1).getName());
            row.add(keyboardButton);
            keyboardRowList.add(row);
        }
        KeyboardRow row = new KeyboardRow();
        KeyboardButton back = new KeyboardButton(Constants.BACK);
        KeyboardButton mainMenu = new KeyboardButton(Constants.MAIN_MENU_BUTTON);
        row.add(back);
        row.add(mainMenu);
        keyboardRowList.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    @Override
    public SendMessage sendMessageWithProduct(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(Constants.AMOUNT_TEXT);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        //1-9
        for (int i = 1; i < 10; i+=3) {
            KeyboardRow row = new KeyboardRow();
            KeyboardButton button = new KeyboardButton(String.valueOf(i));
            KeyboardButton button1 = new KeyboardButton(String.valueOf(i+1));
            KeyboardButton button2 = new KeyboardButton(String.valueOf(i+2));
            row.add(button);
            row.add(button1);
            row.add(button2);
            keyboardRowList.add(row);
        }
        KeyboardRow row = new KeyboardRow();
        KeyboardButton back = new KeyboardButton(Constants.BACK);
        KeyboardButton basket = new KeyboardButton(Constants.BASKET);
        row.add(back);
        row.add(basket);
        keyboardRowList.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;
    }

    @Override
    public SendPhoto selectProduct(Update update) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(update.getMessage().getChatId()));

        Optional<Book> optionalBook = bookRepository.findByName(update.getMessage().getText());
        Book book = optionalBook.get();
        Attachment attachment = book.getAttachment();

        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(attachment.getId());

        AttachmentContent attachmentContent = optionalAttachmentContent.get();


        StringBuilder builder = new StringBuilder();
        builder.append(book.getName())
                .append("\n")
                .append(book.getCategory().getName())
                .append("\n")
                .append(book.getDescription())
                .append("\n")
                .append(book.getPrice())
                .append("\n");
        sendPhoto.setCaption(String.valueOf(builder));
        //shu yerda Artachmentni olib kelish kk productnikimi
        InputFile inputFile = new InputFile(new ByteArrayInputStream(attachmentContent.getAsosiyContent()), attachmentContent.getAttachment().getFileOriginalName());
        sendPhoto.setPhoto(inputFile);
        return sendPhoto;
    }

    @Override
    public SendMessage selectCategory(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(Constants.PRODUCTS);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        String categoryName = update.getMessage().getText();
        List<Book> all = bookRepository.findAllByCategory_Name(categoryName);
        int size = all.size();
        int r = (size % 2 == 1) ? size - 1 : size;
        for (int i = 0; i < r; i = i + 2) {
            KeyboardRow row1 = new KeyboardRow();
            KeyboardButton button = new KeyboardButton(all.get(i).getName());
            KeyboardButton button1 = new KeyboardButton(all.get(i + 1).getName());
            row1.add(button);
            row1.add(button1);
            keyboardRowList.add(row1);
        }
        if (size % 2 == 1) {
            KeyboardRow row = new KeyboardRow();
            KeyboardButton keyboardButton = new KeyboardButton();
            keyboardButton.setText(all.get(size - 1).getName());
            row.add(keyboardButton);
            keyboardRowList.add(row);
        }

        KeyboardRow row = new KeyboardRow();
        KeyboardButton back = new KeyboardButton(Constants.BACK);
        KeyboardButton mainMenu = new KeyboardButton(Constants.MAIN_MENU_BUTTON);
        row.add(back);
        row.add(mainMenu);
        keyboardRowList.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    @Override
    public SendMessage myOrders(Update update) {
        return null;
    }

    @Override
    public SendMessage settings(Update update) {
        return null;
    }

    @Override
    public SendMessage aboutUs(Update update) {
        return null;
    }

    @Override
    public SendMessage comment(Update update) {
        return null;
    }



}
