package beans;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Reference;

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
	//facciamo un riferimento a una collection esterna (DBRef)
	
	@Reference(value="id")
	private Utente utente;
	private Long chatid;
	//umberto: come esempio di oggetto embeddato in un altro
	//@Embedded // let's us embed a complex object
//    private Person person;
	
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
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public Long getChatid() {
		return chatid;
	}
	public void setChatid(Long chatid) {
		this.chatid = chatid;
	}
	@Override
	public String toString() {
		return "Image [id=" + id + ", url=" + url + ", category=" + category + ", Group=" + Group + ", utente=" + utente
				+ ", chatid=" + chatid + "]";
	}
	
	
	
	
	
	
	
}
