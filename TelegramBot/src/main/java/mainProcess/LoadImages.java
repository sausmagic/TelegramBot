package mainProcess;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.mongodb.morphia.Datastore;

import beans.Image;
import beans.Utente;
import database.impl.DatabaseManager;
import database.impl.DatabaseOperationsImpl;
import enumerations.Collections;
import enumerations.Group;
import utils.MD5Generator;

public class LoadImages extends DatabaseOperationsImpl{

	private static Datastore datastore;
	
	public LoadImages(String environment) {
		super(environment);
		getOpenDatabaseConnection();
		datastore = getDataStore();
		}

	private static Image image;

	
	public boolean loadImages(List<String> result, String categoryToInsert) {
		boolean isloaded = false;
		if (categoryToInsert.equals(Group.Category.GIRL.getCategoryName())) {
			for (String ImageUrl : result) {
				image = new Image();
				String id = "";
				try {
					id = MD5Generator
							.getHash(Group.Category.GIRL.getCategoryName() + Group.SEXY.getGroupName() + ImageUrl);
					System.out.println("MD5 generato: "+id);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (datastore.createQuery(Image.class).field("id") != null) {
					int searchImage = datastore.createQuery(Image.class).field("id").equalIgnoreCase(id)
							.asList().size();
					if (searchImage <= 0) {
						image.setCategory(Group.Category.GIRL.getCategoryName());
						image.setGroup(Group.SEXY.getGroupName());
						image.setUrl(ImageUrl);
						image.setId(id);
						datastore.save(image);
						System.out.println("Salvo imaggine a DB");
					} else {
						System.out.println("Immagine presente nel database");
					}
				}else {
					System.out.println("Database vuoto....");
				}
			}
		} else {
			System.out.println(String.format("La categoria %s specificata non è definita!", categoryToInsert));
		}

		return isloaded;

	}

	public List<Image> getAllImages(String categoryToInsert) {
		return datastore.find(Image.class).asList();

	}
}
