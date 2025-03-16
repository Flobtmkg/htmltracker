package flo.web.htmltracker.jobs;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import flo.web.htmltracker.Entities.Tracker;
import flo.web.htmltracker.enums.ErrorMode;
import flo.web.htmltracker.enums.TrackingFrequency;
import flo.web.htmltracker.repositories.TrackerRepository;
import flo.web.htmltracker.services.MailService;
import flo.web.htmltracker.services.TrackerService;

@Service
public class TrackerJobs {
	
	@Autowired
	private TrackerRepository trackerRepository;
	
	@Autowired
	private TrackerService trackerService;
	
	@Autowired
	private MailService mailService;
	
	
	@Scheduled(cron="0 */5 * * * *")
	public void minutely() {
		mainTrackerJob(TrackingFrequency.MINUTELY);
	}
	
	
	@Scheduled(cron="0 0 */1 * * *")
	public void hourly() {
		mainTrackerJob(TrackingFrequency.HOURLY);
	}
	
	
	@Scheduled(cron="0 0 0 */1 * *")
	public void daily() {
		mainTrackerJob(TrackingFrequency.DAILY);
	}
	
	@Scheduled(cron="0 0 0 1 */1 *")
	public void monthly() {
		mainTrackerJob(TrackingFrequency.MONTHLY);
	}
	
	
	
	private void mainTrackerJob(TrackingFrequency frequency) {
		// find trackers from database
		List<Tracker> trackers = trackerRepository.findAllEnableTrackerByTrackingFrequency(frequency.getFrequencyValue());
		
		if(null != trackers && trackers.size() > 0) {
			// Get the list of trackers which target has changed 
			List<Tracker> trackersNOK = trackers.parallelStream()
					.map(tracker -> trackerService.testIfTrackerIsNOK(tracker, ErrorMode.ERROR_IS_NOK))
					.filter(tracker -> tracker != null)
					.collect(Collectors.toList());
			
			// Send email to warn that the target has changed 
			trackersNOK.parallelStream()
				.forEach(tracker -> mailService.sendEmailForNOKTracker(tracker));
			
			// Triggered tracker => disabled
			trackersNOK.stream()
				.forEach(tracker -> {
					tracker.setEnable(false);
					trackerRepository.save(tracker);
					}
				);
		}
		
	}

}
