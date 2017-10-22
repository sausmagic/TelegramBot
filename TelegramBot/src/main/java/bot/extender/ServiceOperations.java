package bot.extender;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import bot.SausmagicBot;
import enumerations.Collections;

/**
 * Questa classe fa da Handler spoof di quello che accade nella chat.
 * Puo provvedere a intercettare messaggi nella chat e storarli a DB o manipolarli in BE.
 * alla fine verso la chat il comportamento sarà quello di default definito nel Bot padre esteso
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
//				db_op.check(user.getFirstName(), user.getLastName(), user.getId(), user.getUserName());
				db_op.checkUtente(user, chat_id);
				db_op.getcollectionData(Collections.USERS.getCollectionName());
				
				db_op.getcollectionData(Collections.UTENTI.getCollectionName());
				// riporto al comportamento definito nel bot base se il message è diverso da
				// testo o non ha il comportamento che vogliamo
				super.onUpdateReceived(update);
			} else {
				// riporto al comportamento definito nel bot base se il message è diverso da
				// testo o non ha il comportamento che vogliamo
				super.onUpdateReceived(update);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Eccezione: "+e);
		} finally {
//			System.out.println("Chiudo la connessione con il DB.");
//			db_op.closeConnection();
		}
	}
}
