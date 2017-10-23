package mainProcess;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import beans.Image;
import database.impl.DatabaseOperationsImpl;
import enumerations.Group;
import enumerations.Group.Category;
import utils.MD5Generator;

public class ImageFactory extends DatabaseOperationsImpl {

	// private static Datastore datastore;

	public ImageFactory(String environment) {
		super(environment);
	}

	public ImageFactory() {
		super(System.getenv("env"));
	}

	private static Image image;

	/**
	 * orchestratore inserimento delle immagini a DB
	 * 
	 * @param result
	 * @param categoryToInsert
	 * @return
	 */
	public boolean loadImages(List<String> result, String categoryToInsert) {
		boolean isloaded = false;
		if (categoryToInsert.equals(Group.Category.GIRL.getCategoryName())) {
			isloaded = insertToDatabase(Group.Category.GIRL.getCategoryName(), Group.SEXY.getGroupName(), result);
		} else if (categoryToInsert.equals(Group.Category.AUTO.getCategoryName())) {
			isloaded = insertToDatabase(Group.Category.AUTO.getCategoryName(), Group.SPORT.getGroupName(), result);
		} else {
			System.out.println(String.format("La categoria %s specificata non è definita!", categoryToInsert));
		}

		return isloaded;

	}

	private boolean insertToDatabase(String category, String group, List<String> result) {
		boolean isloaded = false;
		for (String ImageUrl : result) {
			image = new Image();
			String id = "";
			try {
				id = getMD5Key(category, group, ImageUrl);
				System.out.println("MD5 generato: " + id);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (getDataStore().createQuery(Image.class).field("id") != null) {
				int searchImage = getDataStore().createQuery(Image.class).field("id").equalIgnoreCase(id).asList()
						.size();
				if (searchImage <= 0) {
					image.setCategory(category);
					image.setGroup(group);
					image.setUrl(ImageUrl);
					image.setId(id);
					getDataStore().save(image);
					System.out.println("Salvo imaggine a DB");
				} else {
					System.out.println("Immagine presente nel database");
				}
			} else {
				System.out.println("Database vuoto....");
			}
			isloaded = true;
		}
		return isloaded;

	}

	private String getMD5Key(String category, String group, String imageUrl) throws NoSuchAlgorithmException {
		return MD5Generator.getHash(Group.Category.GIRL.getCategoryName() + Group.SEXY.getGroupName() + imageUrl);

	}

	public List<Image> getAllImages(Category categoryToSearch) {
		List<Image> toReturn = new ArrayList<Image>();

		switch (categoryToSearch) {
		case ALL:
			toReturn = getDataStore().find(Image.class).asList();
			break;
		case AUTO:
			toReturn = getListImage(Group.Category.AUTO.getCategoryName());	
			break;
		case CALCIO:
			toReturn = getListImage(Group.Category.CALCIO.getCategoryName());	
			break;
		case GIRL:
			toReturn = getListImage(Group.Category.GIRL.getCategoryName());
			break;
		case MOTO:
			toReturn = getListImage(Group.Category.MOTO.getCategoryName());
			break;
		default:
			break;
		}
		return toReturn;

	}

	private List<Image> getListImage(String categoryName) {
		List<Image> tempList = new ArrayList<Image>();
		List<Image> toReturn = new ArrayList<Image>();
		tempList= getDataStore().find(Image.class).asList();
		for (Image image : tempList) {
			if(image.getCategory().equals(categoryName)) {
				toReturn.add(image);
			}
		}
		return toReturn;
		
	}

	/**
	 * Da gestire meglio...... vedere nello specifico uso api Morphia
	 * 
	 * @param categoryName
	 * @return
	 */
	public boolean deleteImageToDb(String categoryName) {
		boolean isRemoved = false;
		for (Image image : getDataStore().find(Image.class).asList()) {
			if (image.getCategory().equals(categoryName)) {
				getDataStore().delete(image);
				System.out.println("Rimosso dal DB: " + image);
			}
			isRemoved = true;
		}
		return isRemoved;

	}
}
