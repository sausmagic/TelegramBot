package yaml.configuration;

import java.io.File;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import test.YamlConfigRunnerJakson;

public class YamlManager {
	
	private final String FILENAME = "properties/properties.yaml";
	private ConfigYaml configurationProperties;
	
	public ConfigYaml startConfiguration() {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try {
			YamlConfigRunnerJakson test = new YamlConfigRunnerJakson();
			configurationProperties = mapper.readValue(test.getFileResourceProperties(FILENAME), ConfigYaml.class);
			System.out.println(ReflectionToStringBuilder.toString(configurationProperties, ToStringStyle.MULTI_LINE_STYLE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configurationProperties;
	}
	
	private File getFileResourceProperties(final String filename) {
		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(filename).getFile());
		return file;
	}

}
