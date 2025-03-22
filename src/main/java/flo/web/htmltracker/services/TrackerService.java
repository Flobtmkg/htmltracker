package flo.web.htmltracker.services;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tika.parser.txt.CharsetDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;

import flo.web.htmltracker.Entities.Tracker;
import flo.web.htmltracker.Entities.Url;
import flo.web.htmltracker.enums.ErrorMode;
import flo.web.htmltracker.exceptions.TrackerNotFoundException;
import flo.web.htmltracker.repositories.TrackerRepository;

@Service
public class TrackerService {
	
	@Autowired
	private TrackerRepository trackerRepository;
	
	public void addTracker(Tracker contentToTrack) {
		contentToTrack.setEnable(true);
		trackerRepository.save(contentToTrack);
	}
	
	public void updateTracker(Tracker contentToTrack) {
		trackerRepository.save(contentToTrack);
	}
	
	public void deleteTracker(Integer id) {
		trackerRepository.deleteById(id);
	}
	
	public Tracker findTracker(Integer id) throws TrackerNotFoundException {
		return trackerRepository
				.findById(id)
				.orElseThrow(() -> new TrackerNotFoundException());
	}
	
	
	
	public boolean checkPassword(Tracker tracker, String password) {
		if(null != tracker && tracker.getHashPassword().equals(Hashing.sha384().hashString(password, StandardCharsets.UTF_8).toString())) {
			return true;
		}
		return false;
	}
	
	
	// Return tracker if content has changed (so if tracker is NOK) or if we have an error (in case of ErrorMode.ERROR_IS_NOK)
	public Tracker testIfTrackerIsNOK(Tracker tracker, ErrorMode errorMode) {

		String responseString = null;
		
		try {
			responseString = findHTMLCodeFromURL(tracker.getUrlToTrack());
		} catch (Exception e) {
			if(ErrorMode.ERROR_IS_NOK == errorMode)
				// if tracker was added, it means that originally there was no error
				// if then an error get triggered, we decide that it means that the content has changed
				return tracker;
			else {
				return null;
			}
		}
		// Check if present
		if(responseString.replace(" ", "").replace("\r","").replace("\n","").indexOf(tracker.getUnformatTextToTrack()) > 0) {
			return null;
		}
		return tracker;
	}
	

	
	public String findHTMLCodeFromURL(Url url) throws Exception {
		
		HttpUriRequest request = new HttpGet(url.getUrlString());
		request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:136.0) Gecko/20100101 Firefox/136.0");
		request.setHeader("Sec-Fetch-Mode", "navigate");
		request.setHeader("Sec-Fetch-Dest", "document");
		request.setHeader("Referer", "https://www.google.com/");
		request.setHeader("Sec-Fetch-User", "?1");
		
		try(CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(request)){
						
				CharsetDetector detector = new CharsetDetector();
				byte[] responseBytes = EntityUtils.toByteArray(response.getEntity());
				detector.setText(responseBytes);
				return new String(responseBytes, detector.detect().getName());
						
			} catch (Exception e) {
				throw e;
			}
	}
}
