package bot;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

import beans.Image;
import database.IDatabaseOperations;
import database.impl.DatabaseOperationsImpl;
import enumerations.Group;
import mainProcess.ImageFactory;
import yaml.configuration.ConfigYaml;
import yaml.configuration.YamlManager;

/**
 * Classe base del boot nel caso creare delle classi più rqaffinate ed estendere
 * questa che estende la classe API di telegram per il polling sul boot in
 * hadler su cosa accade nella chat.
 * 
 * @author u.palo
 *
 */
public class SausmagicBot extends TelegramLongPollingBot {

	private static final BotLogger LOGGER = new BotLogger();

	private ImageFactory imageService = new ImageFactory();

	private String messageToSent = "";
	String message_text;
	long chat_id;
	User user;
	private ConfigYaml configYaml;
	public IDatabaseOperations db_op;
	private List<Image> listImage;
	private List<Image> listImageAuto;
	private List<Image> listImageSexy;
	private List<Image> listImageUsers;

	public SausmagicBot() {
		super();
		init();
	}

	private void init() {
		// inizializzazione configurazione
		configYaml = YamlManager.getConfigYaml();
		// inizializzazione MAnagerFactory per connessione a DB
		db_op = new DatabaseOperationsImpl(System.getenv("env"));
		listImage = imageService.getAllImages(Group.Category.ALL);
		System.out.println(
				"Immagine caricate dal databse: " + listImage.size() != null ? listImage.size() + "immagini TOTALI "
						: "non ci sono immagini a BD");
		listImageAuto = imageService.getAllImages(Group.Category.AUTO);
		System.out.println("Immagine AUTO caricate dal databse: " + listImageAuto.size() != null
				? listImageAuto.size() + "immagini AUTO "
				: "non ci sono immagini a BD");
		listImageSexy = imageService.getAllImages(Group.Category.GIRL);
		System.out.println("Immagine SEXY caricate dal databse: " + listImageSexy.size() != null
				? listImageSexy.size() + "immagini SEXY "
				: "non ci sono immagini a BD");
		listImageUsers = imageService.getAllImages(Group.Category.INTERNAL_USERS);
		System.out.println("Immagine SEXY caricate dal databse: " + listImageUsers.size() != null
				? listImageUsers.size() + "immagini USERS "
				: "non ci sono immagini a BD");

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

		user = update.getMessage().getFrom();
		String name_User = user.getFirstName();
		if (update.hasMessage() && update.getMessage().hasText()) {
			// Set variables
			message_text = update.getMessage().getText();

			chat_id = update.getMessage().getChatId();

			if (message_text.equals("statistic")) {
				messageToSent = "statistiche: ";
//				db_op.getStatisticUser(user);
				db_op.getStatisticUserByMorphia(user);
//				SendMessage message = new SendMessage() // Create a message object object
//						.setChatId(chat_id).setText(messageToSent);
//				try {
//					execute(message); // Sending our message object to user
//				} catch (TelegramApiException e) {
//					e.printStackTrace();
//				}
			} else if (message_text.equals("cacca")) {
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
				// User sent /pic
				SendPhoto msg = new SendPhoto().setChatId(chat_id).setPhoto(
						"https://i0.wp.com/www.roadtvitalia.it/wp-content/uploads/2015/09/foto-belle-donne-sportive-10.jpg?zoom=2&w=1136&h=852&crop")
						.setCaption(name_User + " Sei un Malament....");
				try {
					sendPhoto(msg); // Call method to send the photo
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			} else if (message_text.equals("bellafiga")
					|| message_text.toLowerCase().matches(".*bella.*|.*figa.*|.*culo.*|.*tette.*")) {
				LOGGER.info("invio", "photo");
				String urlimage = listImageSexy.get(ThreadLocalRandom.current().nextInt(0, listImageSexy.size() + 1))
						.getUrl().trim();
				System.out.println("Urlimage caricata: " + urlimage);
				SendPhoto msg = new SendPhoto().setChatId(chat_id).setPhoto(urlimage)
						.setCaption(name_User + " Sì nu' Zuzzus' beccati questa ....");
				try {
					sendPhoto(msg); // Call method to send the photo
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			} else if (message_text.toLowerCase().contains("ferrari")) {
				LOGGER.info("invio", "photo");
				String urlimage = listImageAuto.get(ThreadLocalRandom.current().nextInt(0, listImageAuto.size() + 1))
						.getUrl().trim();
				System.out.println("Urlimage caricata: " + urlimage);
				SendPhoto msg = new SendPhoto().setChatId(chat_id).setPhoto(urlimage)
						.setCaption(name_User + " Te' piacess....");
				try {
					sendPhoto(msg); // Call method to send the photo
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			} else if (message_text.equals("refresh")) {
				LOGGER.info("Informazione", "eseguo refresh delle liste richiamando la init()....");
				init();
			} else {
				messageToSent = message_text;
				System.out.println("in chat è stato scritto questo da: " + name_User + " --> " + messageToSent);

				// SendMessage message = new SendMessage() // Create a message object object
				// .setChatId(chat_id).setText(messageToSent);
				// try {
				// execute(message); // Sending our message object to user
				// } catch (TelegramApiException e) {
				// e.printStackTrace();
				// }
			}

		}
		// Qui carichiamo l'immagine inviata sul server di telegram che ci fornisce l'id
		// associato all'immagine da utilizzare per recuperarla
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
			// SendPhoto msg = new
			// SendPhoto().setChatId(chat_id).setPhoto(f_id).setCaption(caption);
			// try {
			// sendPhoto(msg); // Call method to send the photo with caption
			// } catch (TelegramApiException e) {
			// e.printStackTrace();
			// }
		} else {
			// se nessuno degli altri andiamo in echo message
			messageToSent = message_text;
			System.out.println("in chat è stato scritto questo da: " + name_User + " --> " + messageToSent);
			// SendMessage message = new SendMessage() // Create a message object object
			// .setChatId(chat_id).setText(messageToSent);
			// try {
			// execute(message); // Sending our message object to user
			// } catch (TelegramApiException e) {
			// e.printStackTrace();
			// }
		}
	}

	@Override
	public String getBotToken() {
		return configYaml.getTelegramProperties().getToken();
	}

	public static void main(String[] args) {
		String bella = "fig";
		if (bella.matches(".*bella.*|.*figa.*|.*culo.*|.*tette.*")) {
			System.out.println("trovato");
		}
	}

}
