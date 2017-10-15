package test;

import java.io.File;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import yaml.configuration.ConfigYaml;

public class YamlConfigRunnerJakson {

	private final static String filename = "properties/properties.yaml";
	
	public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
        	YamlConfigRunnerJakson test = new YamlConfigRunnerJakson();
        	ConfigYaml user = mapper.readValue(test.getFileResourceProperties(filename), ConfigYaml.class);
            System.out.println(ReflectionToStringBuilder.toString(user,ToStringStyle.MULTI_LINE_STYLE));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
	public File getFileResourceProperties(String filename) {
		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(filename).getFile());
		return file;
	}
}
