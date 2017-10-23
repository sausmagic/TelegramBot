package bot.extender;

import java.util.Comparator;
import java.util.List;

import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import beans.Image;
import bot.SausmagicBot;
import enumerations.Collections;

/**
 * Questa classe fa da Handler spoof di quello che accade nella chat. Puo
 * provvedere a intercettare messaggi nella chat e storarli a DB o manipolarli
 * in BE o quello che cazzo volete fare rimanendo pulito la classe che estende
 * TelegramLongPollingBot. Di default vengono girati al ROOt bot nel caso di
 * ulteriori manipolazioni (ma preferibile farle qui o estendere questa classe
 * se si vuole dettagliare e/o espandere le funzionalità qui presenti)
 * 
 * @author u.palo
 *
 */
public class ServiceOperations extends SausmagicBot {
	String message_text;
	User user;
	long chat_id;

	@Override
	public void onUpdateReceived(Update update) {
		try {
			if (update.hasMessage() && update.getMessage().hasText()) {
				message_text = update.getMessage().getText();
				chat_id = update.getMessage().getChatId();
				user = update.getMessage().getFrom();
				// db_op.check(user.getFirstName(), user.getLastName(), user.getId(),
				// user.getUserName());
				db_op.checkUtente(user, chat_id);
				db_op.getcollectionData(Collections.USERS.getCollectionName());

				db_op.getcollectionData(Collections.UTENTI.getCollectionName());
				// riporto al comportamento definito nel bot base se il message è diverso da
				// testo o non ha il comportamento che vogliamo
				super.onUpdateReceived(update);
			} else if (update.hasMessage() && update.getMessage().hasPhoto()) {
				// Message contains photo
				// Set variables
				chat_id = update.getMessage().getChatId();
				user = update.getMessage().getFrom();
				// Array with photo objects with different sizes
				// We will get the biggest photo from that array
				List<PhotoSize> photos = update.getMessage().getPhoto();
				// Know file_id
				String f_id = photos.stream().sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
						.findFirst().orElse(null).getFileId();
				// Know photo width
				int f_width = photos.stream().sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
						.findFirst().orElse(null).getWidth();
				// Know photo height
				int f_height = photos.stream().sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
						.findFirst().orElse(null).getHeight();
				// Set photo caption
				String caption = "file_id: " + f_id + "\nwidth: " + Integer.toString(f_width) + "\nheight: "
						+ Integer.toString(f_height);
				
				System.out.println("carico a DB la foto inviata in chat se non esiste.");
				Image imageUserChat = db_op.storeUserChatPhoto(f_id,f_width,f_height,chat_id,user);
				System.out.println("Inserita a DB l'immagine inserita da: "+user.getFirstName());
				System.out.println("Immagineinserita: "+imageUserChat);
			} else {
				// riporto al comportamento definito nel bot base se il message è diverso da
				// testo o non ha il comportamento che vogliamo
				super.onUpdateReceived(update);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Eccezione: " + e);
		} finally {
			// System.out.println("Chiudo la connessione con il DB.");
			// db_op.closeConnection();
		}
	}
}
