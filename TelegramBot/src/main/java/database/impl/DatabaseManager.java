package database.impl;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import database.IDatabase;
import yaml.configuration.ConfigYaml;
import yaml.configuration.YamlManager;

public class DatabaseManager implements IDatabase {

	protected MongoDatabase database;
	private MongoClient clientConnectionMongoDB;
	private ConfigYaml configYaml;
	private String environment;
	private Datastore datastore;

	public DatabaseManager(String environment) {
		configYaml = YamlManager.getConfigYaml();
		this.environment = environment;
//		init();
	}

	/**
	 * Creazione Oggetto di interfacciamento con il database MongoDB
	 * @param dbname
	 * @return
	 */
	private Datastore getDatastoreIstance(String dbname) {
		final Morphia morphia = new Morphia();

		// tell Morphia where to find your classes
		// can be called multiple times with different packages or classes
//		morphia.mapPackage("org.mongodb.morphia.example");

		// create the Datastore connecting to the default port on the local host
		datastore = morphia.createDatastore(getOpenClientConnection(), dbname);
		datastore.ensureIndexes();
		return datastore;
	}
	
	public Datastore getDataStore() {
		return datastore;
	}

	public void init() {
		System.out.println("test inizializzazione connessione");
		System.out.println("Configurazioni lette e messe in memoria -->" + configYaml);
		openconnectionDB();
	}

	private void openconnectionDB() {
		String URI = configYaml.getConnection(environment).getUrl();
		String dbname = configYaml.getConnection(environment).getDbname();
		database = getConnection(URI, dbname);
		if (database != null) {
			System.out.println("Connession al DB MongoDB avvennuta con successo!!!");
		}
	}

	/**
	 * Se non si hanno info sull'address del db vuol dire che la connessione non esiste o chiusa
	 * @return
	 */
	public MongoDatabase getOpenDatabaseConnection() {
		if(database!=null && clientConnectionMongoDB.getAddress()!=null) {
			return database;
			}else {
				openconnectionDB();
			}
		return database;
	}

	public MongoClient getOpenClientConnection() {
		return clientConnectionMongoDB;
	}

	/**
	 * Umberto: otteniamo la connessione con il DB. Verifichiamo che la connessione
	 * sia effettivamente avvenuta con successo verso il cluster
	 */
	@Override
	public MongoDatabase getConnection(String connectionURI, String db_name) {
		MongoDatabase database =null;

		if (clientConnectionMongoDB == null) {
			System.out.println("Connessione con il DB non presente.... creo connessione");
			MongoClientURI connectionString = new MongoClientURI(connectionURI);
			clientConnectionMongoDB = new MongoClient(connectionString);
			if (clientConnectionMongoDB.getAddress() != null) {
				System.out.println("Connessione eseguita con SUCCESSO al DATABASE");
			} else {
				System.out.println("Impossibile stabilire una connessione SEE Exception Stack Trace");
			}
			if (!clientConnectionMongoDB.isLocked()) {
				System.out.println("Connessione al DB LIBERA!");
			} else {
				System.out.println("Connessione al DB LOCCATA!");
			}
			database = clientConnectionMongoDB.getDatabase(db_name);
			datastore = getDatastoreIstance(db_name);
		} else {
			System.out.println("Connessione DB attiva recupero quella aperta....");
			database = getOpenDatabaseConnection();
			datastore = getDatastoreIstance(db_name);
		}
		
		return database;
	}

	@Override
	public void closeConnection(MongoClient connectionToClose) {
		clientConnectionMongoDB.close();
	}

}
