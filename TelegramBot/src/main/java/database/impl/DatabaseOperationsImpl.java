package database.impl;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import database.IDatabaseOperations;
import database.impl.DatabaseManager;

public class DatabaseOperationsImpl extends DatabaseManager implements IDatabaseOperations { 
	
	public DatabaseOperationsImpl(String environment) {
		super(environment);
	}

	/**
	 * Check utente che scrive il messaggio se non esiste l'ho inserisce a DB con le info associato all'oggetto User Telegram
	 */
	@Override
	public String check(String first_name, String last_name, int user_id, String username) {
		try {
		//apertura nuova connessione: TODO vedere design pattern migliori per migliorare inizialiazzazione
		MongoDatabase database = getOpenDatabaseConnection();
      
      MongoCollection<Document> collection = database.getCollection("users");
      long found = collection.count(Document.parse("{id : " + Integer.toString(user_id) + "}"));
      if (found == 0) {
          Document doc = new Document("first_name", first_name)
                  .append("last_name", last_name)
                  .append("id", user_id)
                  .append("username", username);
          collection.insertOne(doc);
          
          System.out.println("User not exists in database. Written.");
          return "no_exists";
      } else {
          System.out.println("User exists in database.");
          
          return "exists";
      }
		}catch (Exception e) {
			System.err.println("ERRORE:"+e);
		}
		return username;
	}

	@Override
	public void closeConnection() {
		super.closeConnection(getOpenClientConnection());
		
	}

}
