package database.impl;

import java.util.Iterator;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import database.IDatabaseOperations;
import database.impl.DatabaseManager;
import enumerations.Collections;

public class DatabaseOperationsImpl extends DatabaseManager implements IDatabaseOperations {

	public DatabaseOperationsImpl(String environment) {
		super(environment);
	}

	/**
	 * Check utente che scrive il messaggio se non esiste l'ho inserisce a DB con le
	 * info associato all'oggetto User Telegram
	 */
	@Override
	public void check(String first_name, String last_name, int user_id, String username) {
		try {
			// apertura nuova connessione: TODO vedere design pattern migliori per
			// migliorare inizialiazzazione
			MongoDatabase database = getOpenDatabaseConnection();

			MongoCollection<Document> collectionUsers = database.getCollection(Collections.USERS.getCollectionName());
			long found = collectionUsers.count(Document.parse("{id : " + Integer.toString(user_id) + "}"));
			if (found == 0) {
				Document doc = new Document("first_name", first_name).append("last_name", last_name)
						.append("id", user_id).append("username", username);
				collectionUsers.insertOne(doc);

				System.out.println("User not exists in database. Written.");
				
			} else {
				System.out.println("User exists in database.");

				
			}
		} catch (Exception e) {
			System.err.println("ERRORE:" + e);
		}
	}

	@Override
	public void closeConnection() {
		super.closeConnection(getOpenClientConnection());

	}

	@Override
	public void getcollections() {
		try {
			MongoDatabase database = getOpenDatabaseConnection();

			MongoIterable<String> collections = database.listCollectionNames();

			for (String collecName : collections) {
				System.out.println("Collection preenti a DB: "+collecName);
			}

		} catch (Exception e) {
			System.err.println("ERRORE:" + e);
		}
	}

	@Override
	public void getcollectionData(String collectionName) {
		MongoDatabase database = getOpenDatabaseConnection();
		MongoCollection<Document> documents = database.getCollection(collectionName);
		FindIterable<Document> iterableDocuments=documents.find();
		for (Document document : iterableDocuments) {
			System.out.println(document.toJson());
		}
		
	}

	@Override
	public void saveChats(Object objectPojo, String collection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPojoToCollection(Object objectPojo, String collection) {
		// TODO Auto-generated method stub
		
	}

}
