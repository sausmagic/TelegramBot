package database;

/**
 * Interfaccia che definisce le operazioni possibili che si interfacciano con il database
 * @author u.palo
 * @param <T> l'oggetto generico che definisce una classe Pojo associata alla collection sulla quale inserire
 *
 */
public interface IDatabaseOperations<T> {

	/**
	 * Verifica la presenza di un utente che ha inviato un messaggio nella chat nella collection di riferimento.
	 * Se l'utente non esiste l'ho aggiunge alla collection.
	 * Serve per avere uno storico su tutti gli utenti che hanno fatto almeno una volta accesso alla chat di gruppo e inviato un messaggio.
	 * Non tiene conto di utenti joinati nella chat ma che non hanno mai contribuito.
	 * @param first_name
	 * @param last_name
	 * @param user_id
	 * @param username
	 */
	void check(String first_name, String last_name, int user_id, String username);
	/**
	 * Chiude la connessione al DB
	 * NOTA BENE: alla chiusura della connessione e nuova apertura si hanno problemi sul pool di connessione 
	 * definito in fase di connessione e si ha errore di tipo: pool size open.
	 * Studiare la documentazione dell'api di MongoDB per Java
	 */
	void closeConnection();
	/**
	 * Recupera le collection (tabelle su MongoDB) presenti sul database
	 */
	void getcollections();
	
	/**
	 * Recupera i dati presenti in una specifica collection
	 * @param collectionName
	 */
	void getcollectionData(String collectionName);
	/**
	 * Aggiunge un Oggetto custom (POJO) direttamente a DB mappandolo nel formato che accetta MongoDB ovvero un BSON (Binary JSON )
	 * @param objectPojo
	 * @param collection
	 */
	void addPojoToCollection(T objectPojo, String collection);
	/**
	 * Salva le informazioni (messaggi, operazioni ecc...) avvenuti nella chat
	 * @param objectPojo
	 * @param collection
	 */
	void saveChats(T objectPojo, String collection);
}
