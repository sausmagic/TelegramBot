package main.java.bot;

import java.util.Comparator;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

public class SausmagicBot extends TelegramLongPollingBot {

	private static final String TOKEN = "383745201:AAFkSRF1dLKyOOI3jPgfnCCgF0LYoUViEjs";

	private static final BotLogger LOGGER = new BotLogger();
	
	private String messageToSent = "";

	@Override
	public String getBotUsername() {
		return "Sausmagicbot";
	}

	
	@Override
	public void onUpdateReceived(Update update) {
		// We check if the update has a message and the message has text
		
		if (update.hasMessage() && update.getMessage().hasText()) {
			// Set variables
			String message_text = update.getMessage().getText();

			long chat_id = update.getMessage().getChatId();

			if (message_text.contains("cacca")) {
				messageToSent = "sei una merdaccia!";
			} else {
				if (message_text.equals("/pic")) {
					LOGGER.info("invio", "photo");;
					// User sent /pic
					SendPhoto msg = new SendPhoto().setChatId(chat_id)
							.setPhoto("AgADBAADpaoxG0kpCFMMfFFsXSotK_Xu-RkABOITPKXjLGAZAd4BAAEC").setCaption("Photo");
					try {
						sendPhoto(msg); // Call method to send the photo
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
				} else {
					// Unknown command
					SendMessage message = new SendMessage() // Create a message object object
							.setChatId(chat_id).setText("Unknown command");
					try {
						execute(message); // Sending our message object to user
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
				}
				{
					messageToSent = message_text;
				}

				SendMessage message = new SendMessage() // Create a message object object
						.setChatId(chat_id).setText(messageToSent);
				try {
					execute(message); // Sending our message object to user
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
		} else if (update.hasMessage() && update.getMessage().hasPhoto()) {
			// Message contains photo
			// Set variables
			long chat_id = update.getMessage().getChatId();

			// Array with photo objects with different sizes
			// We will get the biggest photo from that array
			List<PhotoSize> photos = update.getMessage().getPhoto();
			// Know file_id
			String f_id = photos.stream().sorted(Comparator.comparing(PhotoSize::getFileSize).reversed()).findFirst()
					.orElse(null).getFileId();
			// Know photo width
			int f_width = photos.stream().sorted(Comparator.comparing(PhotoSize::getFileSize).reversed()).findFirst()
					.orElse(null).getWidth();
			// Know photo height
			int f_height = photos.stream().sorted(Comparator.comparing(PhotoSize::getFileSize).reversed()).findFirst()
					.orElse(null).getHeight();
			// Set photo caption
			String caption = "file_id: " + f_id + "\nwidth: " + Integer.toString(f_width) + "\nheight: "
					+ Integer.toString(f_height);
			SendPhoto msg = new SendPhoto().setChatId(chat_id).setPhoto(f_id).setCaption(caption);
			try {
				sendPhoto(msg); // Call method to send the photo with caption
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public String getBotToken() {
		return TOKEN;
	}

}
