package bot;

import java.util.Comparator;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

import yaml.configuration.ConfigYaml;
import yaml.configuration.YamlManager;

public class SausmagicBot extends TelegramLongPollingBot {

	// private static final String TOKEN =
	// "383745201:AAFkSRF1dLKyOOI3jPgfnCCgF0LYoUViEjs";

	private static final BotLogger LOGGER = new BotLogger();

	private String messageToSent = "";
	String message_text;
	long chat_id;
	private ConfigYaml configYaml;

	public SausmagicBot() {
		super();
		configYaml = YamlManager.getConfigYaml();
	}

	public SausmagicBot(DefaultBotOptions options) {
		super(options);
	}

	@Override
	public String getBotUsername() {
		return "Sausmagicbot";
	}

	@Override
	public void onUpdateReceived(Update update) {
		// We check if the update has a message and the message has text

		if (update.hasMessage() && update.getMessage().hasText()) {
			// Set variables
			message_text = update.getMessage().getText();

			chat_id = update.getMessage().getChatId();

			if (message_text.equals("cacca")) {
				messageToSent = "sei una merdaccia!";
				SendMessage message = new SendMessage() // Create a message object object
						.setChatId(chat_id).setText(messageToSent);
				try {
					execute(message); // Sending our message object to user
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			} else if (message_text.equals("pucceria")) {
				LOGGER.info("invio", "photo");
				;
				// User sent /pic
				SendPhoto msg = new SendPhoto().setChatId(chat_id)
						.setPhoto("AgADBAADpaoxG0kpCFMMfFFsXSotK_Xu-RkABOITPKXjLGAZAd4BAAEC").setCaption("Photo");
				try {
					sendPhoto(msg); // Call method to send the photo
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}

			} else if (message_text.equals("bella")) {
				LOGGER.info("invio", "photo");
				;
				// User sent /pic
				SendPhoto msg = new SendPhoto().setChatId(chat_id)
						.setPhoto("https://i0.wp.com/www.roadtvitalia.it/wp-content/uploads/2015/09/foto-belle-donne-sportive-10.jpg?zoom=2&w=1136&h=852&crop").setCaption("Malament....");
				try {
					sendPhoto(msg); // Call method to send the photo
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			} else {
				messageToSent = message_text;
				SendMessage message = new SendMessage() // Create a message object object
						.setChatId(chat_id).setText(messageToSent);
				try {
					execute(message); // Sending our message object to user
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}

		}
		//Qui carichiamo l'immagine inviata sul server di telegram che ci fornisce l'id associato all'immagine da utilizzare per recuperarla
		else if (update.hasMessage() && update.getMessage().hasPhoto()) {
			// Message contains photo
			// Set variables
			chat_id = update.getMessage().getChatId();

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
		} else {
			// se nessuno degli altri andiamo in echo message
			messageToSent = message_text;
			SendMessage message = new SendMessage() // Create a message object object
					.setChatId(chat_id).setText(messageToSent);
			try {
				execute(message); // Sending our message object to user
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getBotToken() {
		return configYaml.getTelegramProperties().getToken();
	}

}
