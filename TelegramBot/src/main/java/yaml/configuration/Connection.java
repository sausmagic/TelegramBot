package yaml.configuration;

public final class Connection {

	private String url;
	private String dbname;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDBName() {
		return dbname;
	}

	public void setDBName(String dbname) {
		this.dbname = dbname;
	}

	@Override
	public String toString() {
		return String.format("'%s' with name %s", getUrl(), getDBName());
	}

}
