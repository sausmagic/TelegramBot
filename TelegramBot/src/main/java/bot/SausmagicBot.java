package main.java.bot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class SausmagicBot extends TelegramLongPollingBot {

	private static final String TOKEN = "383745201:AAFkSRF1dLKyOOI3jPgfnCCgF0LYoUViEjs";

	private String messageToSent ="";
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

	        if(message_text.contains("cacca")) {
	        	messageToSent="sei una merdaccia!";
	        }else {
	        	messageToSent = message_text;
	        	}
	        
	        SendMessage message = new SendMessage() // Create a message object object
	                .setChatId(chat_id)
	                .setText(messageToSent);
	        try {
	            execute(message); // Sending our message object to user
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
