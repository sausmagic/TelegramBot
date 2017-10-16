package yaml.configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ConfigYaml {

	private Connection[] connection;
	private Connection thisConnection;
	private Map< String, String > users;
	private TelegramProperties telegramProperties;
	private List<String> env;
	
	public Connection getConnection(String environment) {
		for (int i = 0; i < connection.length; i++) {
			if(connection[i].getEnv().equals(environment)) {
				return thisConnection=connection[i];
			}
		}
		//significa che non è stata definita una variabile di ambiente e prendiamo quella di default
		return thisConnection=Arrays.asList(connection).get(0);
	}
	public void setConnection(Connection[] connection) {
		this.connection = connection;
	}
	public Map<String, String> getUsers() {
		return users;
	}
	public void setUsers(Map<String, String> users) {
		this.users = users;
	}
	public TelegramProperties getTelegramProperties() {
		return telegramProperties;
	}
	public void setTelegramProperties(TelegramProperties telegramProperties) {
		this.telegramProperties = telegramProperties;
	}
	public List<String> getEnv() {
		return env;
	}
	public void setEnv(List<String> env) {
		this.env = env;
	}
	
	@Override
	public String toString() {
		return "ConfigYaml [connection=" + thisConnection + ", users=" + users + ", telegramProperties="
				+ telegramProperties + ", env=" + env + "]";
	}
	
	
	
	
	
	
	
}
