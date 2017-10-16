package enumerations;

public enum Collections {

	USERS("users");
	
	private String CollectionName;

	Collections(String CollectionName) {
        this.CollectionName = CollectionName;
    }

    public String getCollectionName() {
        return CollectionName;
    }
}
