package yaml.configuration;

import java.io.Serializable;

public final class Connection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846413523633781532L;
	private String url;
	private String dbname;
	private String env;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	@Override
	public String toString() {
		return "Connection [url=" + url + ", dbname=" + dbname + ", env=" + env + "]";
	}

	

	

}
