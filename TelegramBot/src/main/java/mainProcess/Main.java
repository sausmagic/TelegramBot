package mainProcess;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import beans.Image;
import enumerations.Group;

public class Main {

	//Get file from resources folder
	ClassLoader classLoader = getClass().getClassLoader();
			
	public static void main(String[] args) throws IOException {
	Main a = new Main();
	List<String> result = a.getFile("tmp/tmp2.txt");
	LoadImages a2 = new LoadImages("");
	a2.loadImages(result,Group.Category.GIRL.getCategoryName());
	System.out.println("Inseriti a DB questi valori");
	for (Image image : a2.getAllImages(null)) {
		System.out.println(image);
	}
//	writeFile("/tmp/tmpPulito.txt", result);
	
	}
	
//	private static void writeFile(String filename, List<String> result) throws IOException {
//		classlo
//	    BufferedWriter writer = new BufferedWriter(new FileWriter());
//	    for (String str : result) {
//	    	writer.write(str);
//		}
//	    writer.close();	
//	}

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

			int count=0;
			for (String url : result) {
				count++;
				
				url = url.trim().replaceAll("\"", "");
				System.out.println("["+count+"] -->"+url);
			}
			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;

	  }
}
