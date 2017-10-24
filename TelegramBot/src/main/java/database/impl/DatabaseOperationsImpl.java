package database.impl;

import java.util.Arrays;
import java.util.List;

import org.bson.BsonDocument;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.codecs.BsonTypeClassMap;
import org.bson.codecs.BsonValueCodec;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.telegram.telegrambots.api.objects.User;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import beans.Image;
import beans.Statistics;
import beans.Utente;
import database.IDatabaseOperations;
import database.impl.DatabaseManager;
import enumerations.Collections;
import enumerations.Group;

//Umberto: per usare delle feature di query per join o altro che emulano le funzionalità SQL è necessario importare il package MODEL del driver MongoDB
//Umberto: fondamentale per usare i metodi della famiglia Projection [field, exclude, elemMatch, slice, ecc...	] document.find().projection(fields(include("x", "y"), excludeId()));
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Filters.*;

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
			System.out.println(collectionName + "-->" + document.toJson());
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
		boolean isAlreadyChatRegister = false;

		System.out.println("Verifico la presenza a database di questo utente: " + utente);
		int searchUser = datastore.createQuery(Utente.class).field("id").equalIgnoreCase(utente.getId()).asList()
				.size();

		if (searchUser <= 0) {
			Long[] chatids = { chatId };
			Utente utenteC = transform(utente, chatids);
			datastore.save(utenteC);
			System.out.println("Salvo utente a DB");
		} else {
			// Utente già registrato ma potrebbe essere entrato in una chat diversa con il
			// bot attivo
			Utente utenteC = datastore.createQuery(Utente.class).field("id").equalIgnoreCase(utente.getId()).get();

			// check appartenenza a gruppo chact del bot
			for (int i = 0; i < utenteC.getChatId().length; i++) {
				if (utenteC.getChatId()[i].longValue() == chatId) {
					isAlreadyChatRegister = true;
				}
			}

			// l'utente non ha mai fatto accesso a questa chat lo censiamo
			if (!isAlreadyChatRegister) {
				Long[] chatIds = utenteC.getChatId();
				chatIds = Arrays.copyOf(chatIds, chatIds.length + 1);
				chatIds[utenteC.getChatId().length] = chatId;
				utenteC.setChatId(chatIds);
				// utente aggiornato
				datastore.save(utenteC);
				System.out.println("Utente censito nel nuovo gruppo chat");
			} else {
				System.out.println("Utente presente nel database");
			}
		}
		/**
		 * 
		 * Umberto: difficolta nell'usare nativamente la scrittura dei pojo custom si
		 * utilizza per ora MORPHIA equaivalente hibernate driver ORM
		 * 
		 * //per utilizzare i pojo con MongoDb è necessario definire il Custom
		 * CodecRegistry CodecRegistry pojoCodecRegistry =
		 * CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
		 * CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		 * database = database.withCodecRegistry(pojoCodecRegistry); //
		 * MongoCollection<Utente> collectionUtente = getCollection(database,
		 * Collections.UTENTI.getCollectionName());
		 * 
		 * // Block<Utente> printBlock = new Block<Utente>() { // @Override // public
		 * void apply(final Utente person) { // if (person.getId() == utenteC.getId()) {
		 * // System.out.println("Aggiungo Utente a DB: "+utenteC); //
		 * collectionUtente.insertOne(utenteC); // } else { //
		 * System.out.println("Utente esistente a DB: "+utenteC); // } // } // }; //
		 * collectionUtente.find().forEach(printBlock);
		 * 
		 */
	}

	/**
	 * trasformer interno bean utente custom
	 * 
	 * @param utente
	 * @param chatId
	 * @return
	 */
	private Utente transform(User utente, Long[] chatId) {
		Utente utenteCustom = new Utente();
		utenteCustom.setChatId(chatId);
		utenteCustom.setId(String.valueOf(utente.getId()));
		utenteCustom.setCognome(utente.getLastName());
		utenteCustom.setName(utente.getFirstName());
		utenteCustom.setUsername(utente.getUserName());
		return utenteCustom;
	}

	@Override
	public Image storeUserChatPhoto(String f_id, int f_width, int f_height, long chat_id, User user) {
		Image image = new Image();
		image.setId(f_id);
		image.setChatid(chat_id);
		image.setCategory(Group.Category.INTERNAL_USERS.getCategoryName());
		image.setGroup(Group.INTERNAL_USERS.getGroupName());
		Long[] chatid = { chat_id };
		Utente utente = transform(user, chatid);
		image.setUtente(utente);
		image.setUrl(f_id);
		datastore.save(image);
		return image;

	}

	@Override
	public Statistics getStatisticUser(User user) {
		Statistics stat = new Statistics();
		// TODO: recuperare l'utente mediante id dal DB sulla tabella utente
		Utente utenteC = datastore.createQuery(Utente.class).field("id").equalIgnoreCase(user.getId()).get();
		System.out.println("Utente Recuperato per info su statistiche: " + utenteC);

		// TODO: recuperare con la relazione image per recuperare le immagini inviate
		// nella chat e che ha caricato sul DB
		// si usa la PROJECTION (vedere bene funzionamento su documentazione API)

		// api native driver mongoDB
		MongoCollection<Document> document = database.getCollection(Collections.IMAGE.getCollectionName());

		FindIterable<Document> findIterable = document.find();

		MongoCursor<Document> cursor = findIterable.iterator();
		// document.find(eq("utente.id", utenteC.getId()));
		while (cursor.hasNext()) {

			Document thisDocument = cursor.next();
			CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry());
			final DocumentCodec codec = new DocumentCodec(codecRegistry, new BsonTypeClassMap());
			//Umberto: specifico al builder il tipo di modalità di stampa del json. di default è RELAXED
			JsonWriterSettings jSonWriterSettings = JsonWriterSettings.builder().outputMode(JsonMode.EXTENDED).build();

			System.out.println(thisDocument.toJson(jSonWriterSettings,codec));
			
			
		}
//		for (Document doc : findIterable) {
//			System.out.println(doc.toJson());
//		}

		// Projection con Morphia
		// List<Image> listImage = datastore.createQuery(Image.class).project("utente",
		// true).asList();
		// for (Image image : listImage) {
		// if (image.getUtente() != null) {
		// if (image.getUtente().getId().equals(utenteC.getId())) {
		// System.out.println("Immagine: " + image);
		// }
		// }
		// }
		return stat;
	}

}
