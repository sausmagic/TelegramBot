package bot;


import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import bot.extender.ServiceOperations;
import database.impl.DatabaseManager;


public class Main {

	public static void main(String[] args) {
		
		ApiContextInitializer.init();

		// Instantiate Telegram Bots API
		TelegramBotsApi botsApi = new TelegramBotsApi();

		// Register our bot
		try {
//			botsApi.registerBot(new SausmagicBot());
			botsApi.registerBot(new ServiceOperations());
			
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
