package database;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Interfaccia che definisce i metodi di "interfacciamento" al database
 * @author umbertopalo
 *
 */
public interface IDatabase {

	
	public MongoDatabase getConnection(String connectionURI, String db_name);
	public void closeConnection(MongoClient connectionToClose);
}
