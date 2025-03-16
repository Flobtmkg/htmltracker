package flo.web.htmltracker.validators;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import flo.web.htmltracker.Entities.Tracker;
import flo.web.htmltracker.enums.ErrorMode;
import flo.web.htmltracker.enums.TrackingFrequency;
import flo.web.htmltracker.services.TrackerService;

@Service
public class TrackerValidator implements Validator {
	
	@Autowired
	private URLValidator urlValidator;
	
	@Autowired
	private TrackerService trackerService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Tracker.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Tracker contentToTrack = (Tracker) target;
		
		// URL validation
		urlValidator.validate(contentToTrack.getUrlToTrack(), errors);
		
		
		// Empty content validation
		if(null == contentToTrack.getTextToTrack() || "".equals(contentToTrack.getTextToTrack())){
			errors.reject("The content to track can not be empty.");
		}
		
		
		// Content validation, if NOK or error --> error
		if(null != trackerService.testIfTrackerIsNOK(contentToTrack, ErrorMode.ERROR_IS_NOK)) {
			errors.reject("The content to track has to be present in the request response.");
		}
	
		
		// Email validation
		if(!EmailValidator.getInstance().isValid(contentToTrack.getEmail())) {
			errors.reject("The provided email for tracking results is invalid.");
		}
		
		
		// Name validation
		if(null == contentToTrack.getName() || "".equals(contentToTrack.getName())) {
			errors.reject("The tracker name can not be empty.");
		}
		
		
		// Password validation
		if((null == contentToTrack.getPassword() || "".equals(contentToTrack.getPassword()))
				&& (null == contentToTrack.getHashPassword() || "".equals(contentToTrack.getHashPassword()))) {
			// TODO: Add rules in the backend for passwords ?
			errors.reject("The tracker password should be valid.");
		}
		
		
		// Frequency validation
		try {
			TrackingFrequency.getFrequencyFromString(contentToTrack.getTrackingFrequency());
		} catch(Exception e) {
			errors.reject(e.getMessage());
		}
		
	}
}
