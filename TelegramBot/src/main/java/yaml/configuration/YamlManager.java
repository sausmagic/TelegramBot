package yaml.configuration;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YamlManager {

	private final String FILENAME = "/properties/properties.yaml";
	private ConfigYaml configurationProperties;

	public ConfigYaml startConfiguration() {
		System.out.println("startConfiguration.......START ");
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try {
			YamlConfigRunnerJakson config = new YamlConfigRunnerJakson();
			// configurationProperties =
			// mapper.readValue(test.getFileResourceProperties(FILENAME), ConfigYaml.class);
			if (config.getFileResourceProperties(FILENAME) != null) {
				System.out.println("Leggo il file di configurazione a questo path: "
						+ YamlManager.class.getResource(FILENAME).getPath());
				configurationProperties = mapper.readValue(config.getFileResourceProperties(FILENAME),
						ConfigYaml.class);
				System.out.println(
						ReflectionToStringBuilder.toString(configurationProperties, ToStringStyle.MULTI_LINE_STYLE));
			} else {
				System.err.println("Problema verificatosi con il caricamento del file di properties YAML");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("startConfiguration.......END ");
		return configurationProperties;
	}

	/**
	 * Recupera le configurazioni da properties. Se già istanziato l'oggetto da
	 * altra entità riutiliziamo lo stesso altrimento istanziamo un nuovo oggetto
	 * con le proprietà
	 * 
	 * @return
	 */
	public ConfigYaml getConfigYaml() {
		if (configurationProperties != null) {
			return configurationProperties;
		}
		return configurationProperties = startConfiguration();
	}

}
