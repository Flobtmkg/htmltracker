package flo.web.htmltracker.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Url {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	private String urlString;
	
	public String getUrlString() {
		return urlString;
	}

	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}
	
	public Url(){

	}
	
	public Url(String url){
		this.urlString = url;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
