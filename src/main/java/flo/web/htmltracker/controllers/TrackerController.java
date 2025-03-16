package flo.web.htmltracker.controllers;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import flo.web.htmltracker.Entities.Tracker;
import flo.web.htmltracker.controllers.helpers.RequestHelper;
import flo.web.htmltracker.enums.TrackingFrequency;
import flo.web.htmltracker.services.MailService;
import flo.web.htmltracker.services.TrackerService;
import flo.web.htmltracker.validators.TrackerValidator;

@CrossOrigin(origins = "${allow-origins}")
@Controller
public class TrackerController extends AbstractController {
	
	@Autowired
	private TrackerValidator contentToTrackValidator;
	
	@Autowired
	private TrackerService trackerService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private RequestHelper requestHelper;
	
	
	@GetMapping("/")
	public ModelAndView init(ModelMap map, @RequestParam(value=INFOS_MESSAGE, required=false) String infos) {
		map.addAttribute("frequencyList", Arrays.asList(TrackingFrequency.values()).stream().map(frequency -> frequency.getFrequencyValue()).collect(Collectors.toList()));
		// Set frequency par défaut
		if(!map.containsKey("selectedFrequency")) {
			map.addAttribute("selectedFrequency", TrackingFrequency.HOURLY.getFrequencyValue());
		}
		
		if(null != infos) {
			map.addAttribute(INFOS_MESSAGE, infos);
		}
		requestHelper.addGenericAttributes(map);
		return new ModelAndView("tracker", map);
	}
	
	
	
	@PostMapping("/addtracker")
	public ModelAndView addTracker(Tracker contentToTrack, ModelMap map, Errors errors) {
		
		// Validation
		contentToTrackValidator.validate(contentToTrack, errors);
		if(errors.hasErrors()) {
			map.put("urlToTrack", contentToTrack.getUrlToTrack().getUrlString());
			map.put("selectedFrequency", contentToTrack.getTrackingFrequency());
			map.put("textToTrack", contentToTrack.getTextToTrack());
			map.put("name", contentToTrack.getName());
			map.put("email", contentToTrack.getEmail());
			return returnToPage(errors, map);
		}
		
		// add tracker
		trackerService.addTracker(contentToTrack);
		
		// send confirmation email
		mailService.sendEmailForNewTracker(contentToTrack);
		
		map.put(INFOS_MESSAGE, "The tracker \"" + contentToTrack.getName() + "\" is valid and enable. It has been created as id n°" + contentToTrack.getId() + ". We have sent you a confirmation email.");
		return returnToPage(map);
	}
	
}
