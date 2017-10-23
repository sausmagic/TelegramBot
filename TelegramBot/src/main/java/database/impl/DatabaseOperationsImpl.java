package database.impl;

import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.mongodb.morphia.Datastore;
import org.telegram.telegrambots.api.objects.User;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import beans.Utente;
import database.IDatabaseOperations;
import database.impl.DatabaseManager;
import enumerations.Collections;

public class DatabaseOperationsImpl extends DatabaseManager implements IDatabaseOperations {

	private Datastore datastore;
	
	public DatabaseOperationsImpl() {
		super("");
	}
	
	public DatabaseOperationsImpl(String environment) {
		super(environment);
		getOpenDatabaseConnection();
		datastore = getDataStore();
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
				System.out.println("Collection preenti a DB: " + collecName);
			}

		} catch (Exception e) {
			System.err.println("ERRORE:" + e);
		}
	}

	@Override
	public void getcollectionData(String collectionName) {
		MongoDatabase database = getOpenDatabaseConnection();
		MongoCollection<Document> documents = database.getCollection(collectionName);
		FindIterable<Document> iterableDocuments = documents.find();
		for (Document document : iterableDocuments) {
			System.out.println(collectionName+"-->"+document.toJson());
		}

	}

	@Override
	public void saveChats(Object objectPojo, String collection) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addPojoToCollection(Object utente, String collection) {

	}

	private MongoCollection<Utente> getCollection(MongoDatabase database, String collection) {
		return database.getCollection(collection, Utente.class);
	}

	@Override
	public void checkUtente(User utente, Long chatId) {
		// apertura nuova connessione: TODO vedere design pattern migliori per
		// migliorare inizialiazzazione
		getOpenDatabaseConnection();
		datastore = getDataStore();
		
		Utente utenteC = transform(utente, chatId);
		System.out.println("Verifico la presenza a database di questo utente: "+utenteC);
		int searchUser = datastore.createQuery(Utente.class).field("id").equalIgnoreCase(utente.getId()).asList().size();
		if(searchUser<=0) {
			datastore.save(utenteC);
			System.out.println("Salvo utente a DB");
		}else {
			System.out.println("Utente presente nel database");
		}
/**
 * 
 * Umberto:
 * difficolta nell'usare nativamente la scrittura dei pojo custom si utilizza per ora MORPHIA equaivalente hibernate driver ORM
 * 
		//per utilizzare i pojo con MongoDb è necessario definire il Custom CodecRegistry
				CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
						CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
				database = database.withCodecRegistry(pojoCodecRegistry);
//		MongoCollection<Utente> collectionUtente = getCollection(database, Collections.UTENTI.getCollectionName());
		
//		Block<Utente> printBlock = new Block<Utente>() {
//			@Override
//			public void apply(final Utente person) {
//				if (person.getId() == utenteC.getId()) {
//					System.out.println("Aggiungo Utente a DB: "+utenteC);
//					collectionUtente.insertOne(utenteC);
//				} else {
//					System.out.println("Utente esistente a DB: "+utenteC);
//				}
//			}
//		};
//		collectionUtente.find().forEach(printBlock);
 * 
 */
	}

	/**
	 * trasformer interno bean utente custom 
	 * @param utente
	 * @param chatId
	 * @return
	 */
	private Utente transform(User utente, Long chatId) {
		Utente utenteCustom = new Utente();
		utenteCustom.setChatId(chatId);
		utenteCustom.setId(String.valueOf(utente.getId()));
		utenteCustom.setCognome(utente.getLastName());
		utenteCustom.setName(utente.getFirstName());
		utenteCustom.setUsername(utente.getUserName());
		return utenteCustom;
	}

	@Override
	public void addListUrlImages(List<String> urlImages, String collection) {
		// TODO Auto-generated method stub
		
	}

}
