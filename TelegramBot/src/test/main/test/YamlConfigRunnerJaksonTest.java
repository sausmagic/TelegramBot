package test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import yaml.configuration.ConfigYaml;
import yaml.configuration.Connection;
import yaml.configuration.YamlManager;

public class YamlConfigRunnerJaksonTest {

	private final static String FILENAME = "/properties/properties.yaml";
	
	public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
        	YamlConfigRunnerJaksonTest test = new YamlConfigRunnerJaksonTest();
        	ConfigYaml user = mapper.readValue(test.getFileResourceProperties(FILENAME), ConfigYaml.class);
            System.out.println(ReflectionToStringBuilder.toString(user,ToStringStyle.MULTI_LINE_STYLE));
//            for (Connection connection : user.getConnection("local")) {
//				System.out.println(connection);
//			}
            
            System.out.println(user.getConnection(System.getenv("env")));
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

	}
}
