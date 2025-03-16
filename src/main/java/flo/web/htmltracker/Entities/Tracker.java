package flo.web.htmltracker.Entities;

import java.nio.charset.StandardCharsets;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.google.common.hash.Hashing;

@Entity
public class Tracker {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Url urlToTrack;
	
	@Lob
	private String textToTrack;
	
	@Lob
	private String unformatTextToTrack;
	
	private String trackingFrequency;
	
	private String email;
	
	private String name;
	
	private boolean enable;
	
	@Transient
	private String password;
	
	private String hashPassword;
	
	public Tracker() {
		
	}
	

	public String getTextToTrack() {
		return textToTrack;
	}

	public void setTextToTrack(String textToTrack) {
		this.textToTrack = textToTrack;
		defineUnformatTextToTrack();
	}
	
	
	public String getUnformatTextToTrack() {
		return unformatTextToTrack;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Url getUrlToTrack() {
		return urlToTrack;
	}

	public void setUrlToTrack(Url urlToTrack) {
		this.urlToTrack = urlToTrack;
	}
	
	private void defineUnformatTextToTrack() {
		this.unformatTextToTrack = textToTrack.replace(" ", "").replace("\r","").replace("\n","");
	}

	public String getTrackingFrequency() {
		return trackingFrequency;
	}

	public void setTrackingFrequency(String trackingFrequency) {
		this.trackingFrequency = trackingFrequency;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUnformatTextToTrack(String unformatTextToTrack) {
		this.unformatTextToTrack = unformatTextToTrack;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		this.hashPassword = Hashing.sha384().hashString(password, StandardCharsets.UTF_8).toString();
	}

	public String getHashPassword() {
		return this.hashPassword;
	}

	public void setHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
	}


	public boolean isEnable() {
		return enable;
	}


	public void setEnable(boolean enable) {
		this.enable = enable;
	}

}
