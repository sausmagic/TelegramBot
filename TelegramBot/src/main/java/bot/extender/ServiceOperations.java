package bot.extender;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import bot.SausmagicBot;

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
				db_op.check(user.getFirstName(), user.getLastName(), user.getId(), user.getUserName());
				db_op.getcollections();
				super.onUpdateReceived(update);
			} else {
				// riporto al comportamento definito nel bot base se il message è diverso da
				// testo o non ha il comportamento che vogliamo
				super.onUpdateReceived(update);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
//			System.out.println("Chiudo la connessione con il DB.");
//			db_op.closeConnection();
		}
	}
}
