package bot;


import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import database.impl.DatabaseManager;


public class Main {

	public static void main(String[] args) {
		
		//inizializzazione MAnagerFactory per connessione a DB
		new DatabaseManager(System.getenv("env"));
		
		ApiContextInitializer.init();

		// Instantiate Telegram Bots API
		TelegramBotsApi botsApi = new TelegramBotsApi();

		// Register our bot
		try {
			botsApi.registerBot(new SausmagicBot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
