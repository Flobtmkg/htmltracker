package flo.web.htmltracker;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:mails.properties")
public class MailProperties {
	private static final String PROP_ENABLE = "mail.enable";
	private static final String PROP_HOST = "mail.smtp.host";
	private static final String PROP_PORT = "mail.smtp.port";
	private static final String PROP_FROM = "mail.smtp.from";
	private static final String PROP_TLS = "mail.smtp.starttls.enable";
	private static final String PROP_AUTH = "mail.smtp.auth";
	
		
	@Value("${" + PROP_ENABLE + "}")
	private boolean enable;
	
	@Value("${" + PROP_HOST + "}")
	private String host;
	
	@Value("${mail.username}")
	private String username;
	
	@Value("${mail.password}")
	private String password;
	
	@Value("${" + PROP_PORT + "}")
	private String port;
	
	@Value("${" + PROP_FROM + "}")
	private String from;
	
	@Value("${" + PROP_TLS + "}")
	private String tls;
	
	@Value("${" + PROP_AUTH + "}")
	private String auth;

	
	public Properties getProperties() {
		Properties prop = System.getProperties();
		prop.clear();
		prop.put(PROP_HOST, host);
		prop.put(PROP_PORT, port);
		prop.put(PROP_FROM, from);
		prop.put(PROP_TLS, tls);
		prop.put(PROP_AUTH, auth);
		prop.put(PROP_ENABLE, enable);
		return prop;
	}
	
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getTls() {
		return tls;
	}


	public void setTls(String tls) {
		this.tls = tls;
	}


	public String getAuth() {
		return auth;
	}


	public void setAuth(String auth) {
		this.auth = auth;
	}


	public boolean isEnable() {
		return enable;
	}


	public void setEnable(boolean enable) {
		this.enable = enable;
	}


	@Override
	public String toString() {
		return "MailProperties [enable=" + enable + ", host=" + host + ", username=" + username + ", port=" + port + ", from=" + from + ", tls=" + tls + ", auth=" + auth + "]";
	}
	

}
