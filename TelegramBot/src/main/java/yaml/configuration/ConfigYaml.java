package yaml.configuration;

import java.util.Map;

public class ConfigYaml {

	private Connection connection;
	private Map< String, String > users;
	private TelegramProperties telegramProperties;
	
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
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
	@Override
	public String toString() {
		return "ConfigYaml [connection=" + connection + ", users=" + users + ", telegramProperties="
				+ telegramProperties + "]";
	}
	
	
	
	
	
}
