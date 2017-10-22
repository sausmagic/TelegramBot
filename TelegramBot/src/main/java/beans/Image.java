package beans;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("image")
@Indexes(
	    @Index(fields = {@Field("id"), @Field("category"), @Field("group")})
	)
public class Image implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4668607003068597065L;

	@Id
	private String id;
	private String url;
	private String category;
	private String Group;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getGroup() {
		return Group;
	}
	public void setGroup(String group) {
		Group = group;
	}
	@Override
	public String toString() {
		return "Image [id=" + id + ", url=" + url + ", category=" + category + ", Group=" + Group + "]";
	}
	
	
	
	
}
