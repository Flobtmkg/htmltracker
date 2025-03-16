package flo.web.htmltracker.services;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flo.web.htmltracker.H2ConsoleConfig;
import flo.web.htmltracker.MailProperties;
import flo.web.htmltracker.Entities.Tracker;
import flo.web.htmltracker.authenticators.GmailAuthenticator;

@Service
public class MailService {
	
	private final static String MAILER_VERSION = "Java";
	
	private static Logger log = LoggerFactory.getLogger(MailService.class);
	
	@Autowired
	private MailProperties mailProperties;
	
	@Autowired
	private GmailAuthenticator gmailAuthenticator;
	
	private static final String MESS_TITLE = "<h2>HTML TRACKER APP</h2>";
	private static final String MESS_SUB_TITLE = "<br/><h4>Your tracker has detected a change in the associated HTML target</h4>";
	private static final String MESS_SUB_TITLE_ADD = "<br/><h4>Your new tracker has been created successfully.</h4>";
	private static final String MESS_TRACK_ID = "<br/><label>Tracker id : </label>";
	private static final String MESS_TRACK_NAME = "<br/><label>Tracker name : </label>";
	private static final String MESS_TRACK_URL = "<br/><label>Tracker URL : </label>";
	private static final String MESS_TRACK_PASSWORD_ADD = "<br/><label>Tracker password... we hope you remember ;)</label>";
	private static final String MESS_BOTTOM = "<br/><br/><p><i>Since, this tracker has been deleted automatically.</i></p>";
	private static final String MESS_BOTTOM_ADD = "<br/><br/><p><i>When your tracker will detect a change in your HTML target, you will recieve a notification email.</i></p>";
	private static final String MESS_SUB_BOTTOM = "<br/><br/><p><i>This email has been sent by the HTML tracker app robot. do not respond.</i></p>";
	
	
	
	public void sendEmailForNOKTracker(Tracker tracker) {
		Session session = Session.getDefaultInstance(mailProperties.getProperties(), gmailAuthenticator);
		Message message = new MimeMessage(session);
	    InternetAddress[] internetAddresses = new InternetAddress[1];
	    try {
	    	internetAddresses[0] = new InternetAddress(tracker.getEmail());
	    	message.setRecipients(Message.RecipientType.TO,internetAddresses);
		    message.setSubject("[HTML TRACKER APP] Your tracker (" + tracker.getName() + ") id #" + tracker.getId() + " has detected a change.");

		    String strMessage = MESS_TITLE 
		    		+ MESS_SUB_TITLE 
		    		+ MESS_TRACK_ID + tracker.getId()
		    		+ MESS_TRACK_NAME + tracker.getName()
		    		+ MESS_TRACK_URL + "<a href=\"" + tracker.getUrlToTrack().getUrlString() + "\" >" + tracker.getUrlToTrack().getUrlString() + "</a>"
		    		+ MESS_BOTTOM 
		    		+ MESS_SUB_BOTTOM;
		    
		    message.setContent(strMessage, "text/html; charset=utf-8");
		    message.setHeader("X-Mailer", MAILER_VERSION);
			message.setSentDate(new Date());
		} catch (MessagingException e) {
			// TODO: just log? disable tracker?
			log.error(e.getMessage());
		}
	    sendEmail(message);
	}
	
	
	
	
	public void sendEmailForNewTracker(Tracker tracker) {
		Session session = Session.getDefaultInstance(mailProperties.getProperties(), gmailAuthenticator);
		Message message = new MimeMessage(session);
	    InternetAddress[] internetAddresses = new InternetAddress[1];
	    try {
	    	internetAddresses[0] = new InternetAddress(tracker.getEmail());
	    	message.setRecipients(Message.RecipientType.TO,internetAddresses);
		    message.setSubject("[HTML TRACKER APP] Your tracker (" + tracker.getName() + ") id n°" + tracker.getId() + " has been created.");

		    String strMessage = MESS_TITLE 
		    		+ MESS_SUB_TITLE_ADD 
		    		+ MESS_TRACK_ID + tracker.getId()
		    		+ MESS_TRACK_NAME + tracker.getName()
		    		+ MESS_TRACK_URL + "<a href=\"" + tracker.getUrlToTrack().getUrlString() + "\" >" + tracker.getUrlToTrack().getUrlString() + "</a>"
		    		+ MESS_TRACK_PASSWORD_ADD
		    		+ MESS_BOTTOM_ADD 
		    		+ MESS_SUB_BOTTOM;
		    
		    message.setContent(strMessage, "text/html; charset=utf-8");
		    message.setHeader("X-Mailer", MAILER_VERSION);
			message.setSentDate(new Date());
		} catch (MessagingException e) {
			// TODO: just log? disable tracker?
			log.error(e.getMessage());
		}
	    sendEmail(message);
	}
	
	
	
	private void sendEmail(Message message) {	
	    try {
	    	// if not enable, we ignore without error
	    	if(mailProperties.isEnable()) {
	    		Transport.send(message);
	    	}
	    } catch (MessagingException e) {
	    	// TODO: just log? disable tracker?
	    	log.error(e.getMessage());
	    }
	  }
}
