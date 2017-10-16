package yaml.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YamlConfigRunnerJakson {

	private final static String FILENAME = "properties/properties.yaml";
	
	public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
        	YamlConfigRunnerJakson test = new YamlConfigRunnerJakson();
        	ConfigYaml user = mapper.readValue(test.getFileResourceProperties(FILENAME), ConfigYaml.class);
            System.out.println(ReflectionToStringBuilder.toString(user,ToStringStyle.MULTI_LINE_STYLE));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
	@Deprecated
	public File getFileResourcePropertiesold(String filename) {
		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(filename).getFile());
		return file;
	}
	
	@SuppressWarnings("unused")
	public InputStream getFileResourceProperties(final String filename) {
		InputStream fileStream = null;
		// umberto: leggiamo le configurazione dal file di properties interno al jar
		try {
			fileStream = YamlManager.class.getResource(filename).openStream();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileStream;

		// Get file from resources folder
		// ClassLoader classLoader = getClass().getClassLoader();
		// File file = new File(classLoader.getResource(filename).getFile());
		// return file;
	}
}
