package beans;

import java.util.Arrays;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity("utenti")
@Indexes(
	    @Index(fields = {@Field("id"), @Field("username")})
	)
public class Utente {

	@Id
	@JsonProperty(value="id")
	private String id;
	private String name;
	private String cognome;
	//rappresenta l'informazione delle chatida cui l'utente ha fatto accesso con il bot attivo
	private Long[] chatId;
	private String username;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public Long[] getChatId() {
		return chatId;
	}
	public void setChatId(Long[] chatIds) {
		this.chatId = chatIds;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "Utente [id=" + id + ", name=" + name + ", cognome=" + cognome + ", chatId=" + Arrays.toString(chatId)
				+ ", username=" + username + "]";
	}
	
	
}
