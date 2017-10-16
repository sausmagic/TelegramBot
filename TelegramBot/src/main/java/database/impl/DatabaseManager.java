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
//	private YamlManager yamlManager;
	private ConfigYaml configYaml;
	
	public DatabaseManager() {
		super();
//		yamlManager = new YamlManager();
//		configYaml = yamlManager.startConfiguration();
		configYaml = YamlManager.getConfigYaml();
		init();
		
	}

	public void init() {
		System.out.println("test inizializzazione connessione");
		System.out.println("Configurazioni lette e messe in memoria -->"+configYaml);
		String URI = configYaml.getConnection().getUrl();
		String dbname = configYaml.getConnection().getDBName();
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

	@Override
	public MongoDatabase getConnection(String connectionURI, String db_name) {
		MongoClientURI connectionString = new MongoClientURI(connectionURI);
        clientConnectionMongoDB = new MongoClient(connectionString);
		MongoDatabase database = clientConnectionMongoDB.getDatabase(db_name);
        return database;
	}

	@Override
	public void closeConnection(MongoClient connectionToClose) {
		clientConnectionMongoDB.close();
	}
	
	

}
