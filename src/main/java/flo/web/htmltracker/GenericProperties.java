package flo.web.htmltracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class GenericProperties {
	
	
	@Value("${htmltracker.version}")
	private String htmltrackerVersion;
	

	public String getHtmltrackerVersion() {
		return htmltrackerVersion;
	}

	public void setHtmltrackerVersion(String htmltrackerVersion) {
		this.htmltrackerVersion = htmltrackerVersion;
	}
	
	
}
