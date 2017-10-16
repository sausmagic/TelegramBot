package database.impl;



import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import database.IDatabase;
import yaml.configuration.ConfigYaml;
import yaml.configuration.YamlManager;



public class DatabaseManager implements IDatabase {

	private MongoDatabase database;
	private MongoClient clientConnectionMongoDB;
	private ConfigYaml configYaml;
	private String environment;
	
	public DatabaseManager(String environment) {
		configYaml = YamlManager.getConfigYaml();
		this.environment = environment;
		init();
		
	}

	public void init() {
		System.out.println("test inizializzazione connessione");
		System.out.println("Configurazioni lette e messe in memoria -->"+configYaml);
		String URI = configYaml.getConnection(environment).getUrl();
		String dbname = configYaml.getConnection(environment).getDbname();
		database = getConnection(URI,dbname);
		if(database!=null) {
			System.out.println("Connession al DB MongoDB avvennuta con successo!!!");
		}
	}
	
	public MongoDatabase getOpenDatabaseConnection() {
		return database;
	}
	
	public MongoClient getOpenClientConnection() {
		return clientConnectionMongoDB;
	}

	/**
	 * Umberto: otteniamo la connessione con il DB.
	 * Verifichiamo che la connessione sia effettivamente avvenuta con successo verso il cluster
	 */
	@Override
	public MongoDatabase getConnection(String connectionURI, String db_name) {
		MongoClientURI connectionString = new MongoClientURI(connectionURI);
        clientConnectionMongoDB = new MongoClient(connectionString);
        
        if(clientConnectionMongoDB.getAddress()!=null) {
        	System.out.println("Connessione eseguita con SUCCESSO al DATABASE");
        }else {
        	System.out.println("Impossibile stabilire una connessione SEE Exception Stack Trace");
        }
        if(clientConnectionMongoDB.isLocked()) {
        	System.out.println("Connessione al DB LIBERA!");
        }else {
        	System.out.println("Connessione al DB LOCCATA!");
        }
		MongoDatabase database = clientConnectionMongoDB.getDatabase(db_name);
        return database;
	}

	@Override
	public void closeConnection(MongoClient connectionToClose) {
		clientConnectionMongoDB.close();
	}
	
	

}
