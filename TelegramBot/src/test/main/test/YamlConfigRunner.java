package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.yaml.snakeyaml.Yaml;

import yaml.configuration.ConfigYaml;

public class YamlConfigRunner {

	private final static String filename = "properties/properties.yaml";

	public static void main(String[] args) throws IOException {
		YamlConfigRunner test = new YamlConfigRunner();
		
		File file = test.getFileResourceProperties(filename);

		Yaml yaml = new Yaml();
		try (InputStream in = new FileInputStream(file)) {
			ConfigYaml config = yaml.loadAs(in, ConfigYaml.class);
			System.out.println(config.toString());
		}
	}

	public File getFileResourceProperties(String filename) {
		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(filename).getFile());
		return file;
	}
}
