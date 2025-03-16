package flo.web.htmltracker.authenticators;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flo.web.htmltracker.MailProperties;

@Component
public class GmailAuthenticator extends Authenticator {
	
	@Autowired
	MailProperties mailProperties;
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(mailProperties.getUsername(), mailProperties.getPassword());
    }

}
