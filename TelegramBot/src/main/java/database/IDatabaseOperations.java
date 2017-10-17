package database;

public interface IDatabaseOperations {

	public String check(String first_name, String last_name, int user_id, String username);
	public void closeConnection();
	void getcollections();
	void getcollectionData(String collectionName);
}
