package mainProcess;

import java.util.List;

import org.mongodb.morphia.query.Query;

import beans.Utente;
import database.impl.DatabaseOperationsImpl;

public class UtenteFactory extends DatabaseOperationsImpl {

	public UtenteFactory(String environment) {
		super(environment);
	}

	public UtenteFactory() {
		super(System.getenv("env"));
	}
	
	/**
	 * recupera tutti gli utenti dal DB
	 * @return
	 */
	public List<Utente> getAllUsers(){
		List<Utente> allUsers = getDataStore().find(Utente.class).asList();
		for (Utente utente : allUsers) {
			System.out.println(utente);
		}
		
		return allUsers;
		
	}

	/**
	 * rimuove tutti gli utenti dal DB
	 */
	public void removeAllUsers() {
		
//		final Query<Utente> idOverZero = getDataStore().createQuery(Utente.class)
//                .filter("id >", 0);
//			getDataStore().delete(idOverZero);
		for (Utente utente : getAllUsers()) {
			getDataStore().delete(utente);
		}
		
	}
}
