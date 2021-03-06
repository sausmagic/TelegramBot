package mainProcess;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import beans.Image;
import beans.Utente;
import enumerations.Group;
import enumerations.Group.Category;

public class Main {

	// Get file from resources folder
	ClassLoader classLoader = getClass().getClassLoader();
	private static ImageFactory factoryImage = new ImageFactory();
	private static UtenteFactory userFactory = new UtenteFactory();
	private static Main mainProces = new Main();

	
	public static void main(String[] args) throws IOException {
//		 String filename = "tmp/ferrari.txt";
//		 String filename = "tmp/girl.txt";
		// String category = Group.Category.GIRL.getCategoryName();

		 List<Image> images = getAllImages(Group.Category.ALL);
		 for (Image image : images) {
			if(image.getUrl().endsWith(".svg")) {
				System.out.println(image);
			}
		}
//		 loadImages(filename, mainProces, Group.Category.AUTO.getCategoryName());
		// deleteImageToDb(Group.Category.GIRL.getCategoryName());

//		removeUsers();

	}

	private static void removeUsers() {
		userFactory.removeAllUsers();
	}

	private static List<Image> getAllImages(Category category) {
		for (Image image : factoryImage.getAllImages(category)) {
			System.out.println(image);
		}
		return factoryImage.getAllImages(category);
	}

	private static void deleteImageToDb(String categoryName) {

		factoryImage.deleteImageToDb(categoryName);

	}

	private static void loadImages(String filename, Main mainProces, String category) {

		List<String> result = mainProces.getFile(filename);
		ImageFactory factoryImage = new ImageFactory();
		if (factoryImage.loadImages(result, category)) {
			System.out.println("Inseriti a DB questi valori");
			for (Image image : factoryImage.getAllImages(Group.Category.ALL)) {
				System.out.println(image);
			}
		}

	}

	// private static void writeFile(String filename, List<String> result) throws
	// IOException {
	// classlo
	// BufferedWriter writer = new BufferedWriter(new FileWriter());
	// for (String str : result) {
	// writer.write(str);
	// }
	// writer.close();
	// }

	private List<String> getFile(String fileName) {

		List<String> result = new ArrayList<String>();

		File file = new File(classLoader.getResource(fileName).getFile());

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {

				String line = scanner.nextLine();

				System.out.println(line);
				line = line.replaceAll("\"", "");
				String[] appoggio = line.split(",");
				result = Arrays.asList(appoggio);
			}

			int count = 0;
			for (String url : result) {
				count++;

				url = url.trim().replaceAll("\"", "");
				System.out.println("[" + count + "] -->" + url);
			}
			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;

	}
}
