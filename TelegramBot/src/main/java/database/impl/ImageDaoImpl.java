package database.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.CriteriaContainer;
import org.mongodb.morphia.query.CriteriaContainerImpl;
import org.mongodb.morphia.query.CriteriaJoin;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.telegram.telegrambots.api.objects.User;

import beans.Image;
import beans.Statistics;
import beans.Utente;

//Umberto: per usare delle feature di query per join o altro che emulano le funzionalità SQL è necessario importare il package MODEL del driver MongoDB
//Umberto: fondamentale per usare i metodi della famiglia Projection [field, exclude, elemMatch, slice, ecc...	] document.find().projection(fields(include("x", "y"), excludeId()));
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Filters.*;

public class ImageDaoImpl extends BasicDAO<Image, ObjectId> {

	public ImageDaoImpl(Class<Image> entityClass, Datastore ds) {
		super(entityClass, ds);
	}

	public Statistics getStatisticUserByMorphia(User user, Datastore datastore) {
		// istanziamo l'istanza della classe che permette al driver di Morphia di
		// intercettare ed usare il datasource ottenuto dalla sessione in corso
		new ImageDaoImpl(Image.class, datastore);

		Query<Image> queryImage = createQuery().disableValidation();
		//Qui definiamo la creazione della query per una collection diversa da questa istanza "Image"
		//per farlo usiamo il data source passandogli il riferimento del tipo di classe a cui è associata la collection
		Query<Utente> queryUtente = datastore.createQuery(Utente.class).disableValidation();
		//Qui definiamo il criterio da applicare all'oggetto Document a DB di tipo Utente storato a DB come un BSON
		queryUtente.criteria("id").equal(String.valueOf(user.getId()));
		
//		CriteriaContainer containerCriteria = new CriteriaContainerImpl(CriteriaJoin.AND);
		
		//Qui definiamo il criterio da applicare all'oggetto Document a DB di tipo Image storato a DB come un BSON
		queryImage.criteria("utente").in(queryUtente.asKeyList());
//		Query<Image> query = createQuery().disableValidation().asList();
				//.filter("utente.id =", String.valueOf(user.getId()));
				//.field("utente.id").equal(user.getId());
				//.field("utente.$id").equal(user.getId());
		List<Image> listImage = queryImage.asList();
//		for (Image image : listImage) {
//			System.out.println("IMAGE: "+image);
//			System.out.println("UTENTE: " + image.getUtente());
//		}
		Statistics stat = new Statistics();
		stat.setNumFotoPublicate(listImage.size());
		return stat;

	}
}
