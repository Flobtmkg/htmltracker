package flo.web.htmltracker.controllers;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

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
import flo.web.htmltracker.Entities.Url;
import flo.web.htmltracker.controllers.helpers.RequestHelper;
import flo.web.htmltracker.enums.TrackingFrequency;
import flo.web.htmltracker.exceptions.TrackerNotFoundException;
import flo.web.htmltracker.services.TrackerService;
import flo.web.htmltracker.validators.TrackerValidator;
import flo.web.htmltracker.validators.URLValidator;

@CrossOrigin(origins = "${allow-origins}")
@Controller
public class TrackerManagementController extends AbstractController {
	
	
	@Autowired
	private URLValidator urlValidator;
	
	@Autowired
	private TrackerValidator contentToTrackValidator;
	
	@Autowired
	private TrackerService trackerService;
	
	@Autowired
	private RequestHelper requestHelper;

	
	
	@GetMapping("/managetracker")
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
		return new ModelAndView("trackerManagement", map);
	}
	
	
	
	
	@PostMapping("/gettracker")
	public ModelAndView searchTracker(@RequestParam String id, @RequestParam String password, ModelMap map) {
		
		Integer id_num = null;
		Tracker tracker = null;
		
		if(null == password || null == id || "".equals(password) || "".equals(id)) {
			map.put(ERRORS_MESSAGE, "The tracker id and the password can not be empty.");
		}
		try {
			id_num = Integer.valueOf(id);
		} catch(NumberFormatException e) {
			map.put(ERRORS_MESSAGE, "The tracker id should be a number.");
		}
		// Error management
		if(map.containsKey(ERRORS_MESSAGE)) {
			return returnToPage(map);
		}
		
		try {
			tracker = trackerService.findTracker(id_num);
		} catch (TrackerNotFoundException e) {
			map.put(ERRORS_MESSAGE, e.getMessage());
			return returnToPage(map);
		}
		
		if(!trackerService.checkPassword(tracker, password)) {
			map.put(ERRORS_MESSAGE, "The tracker password is invalid.");
			return returnToPage(map);
		}
		
		// if we get there, everything is OK
		map.put("urlToTrack", tracker.getUrlToTrack().getUrlString());
		map.put("selectedFrequency", tracker.getTrackingFrequency());
		map.put("textToTrack", tracker.getTextToTrack());
		map.put("name", tracker.getName());
		map.put("email", tracker.getEmail());
		map.put("enable", tracker.isEnable());
		map.put("id", tracker.getId());
		return returnToPage(map);
	}
	
	
	
	
	@PostMapping("/checkurlformodif")
	public ModelAndView testLinkContent(Url urlToTest, ModelMap map, Errors errors) {
		
		// Error management
		urlValidator.validate(urlToTest, errors);
		if(errors.hasErrors()) {
			map.put("urlString", urlToTest.getUrlString());
			return returnToPage(errors, map);
		}
		
		String responseString = null;
		
		try {
			responseString = trackerService.findHTMLCodeFromURL(urlToTest);
		} catch (Exception e) {
			errors.reject(e.getMessage());
			map.put("urlString", urlToTest.getUrlString());
			return returnToPage(errors, map);
		}
		
		map.put("htmlResponse", responseString);
		map.put("urlString", urlToTest.getUrlString());
		return returnToPage(map);
	}
	
	
	
	@PostMapping("/updatetracker")
	public ModelAndView updateTracker(Tracker inputTracker, ModelMap map, Errors errors, HttpServletRequest request) {
		
		// Error management
		contentToTrackValidator.validate(inputTracker, errors);
		if(errors.hasErrors()) {
			map.put("urlToTrack", inputTracker.getUrlToTrack().getUrlString());
			map.put("selectedFrequency", inputTracker.getTrackingFrequency());
			map.put("textToTrack", inputTracker.getTextToTrack());
			map.put("name", inputTracker.getName());
			map.put("enable", inputTracker.isEnable());
			map.put("email", inputTracker.getEmail());
			return returnToPage(errors, map);
		}
		
		Tracker bddTracker;
		try {
			bddTracker = trackerService.findTracker(inputTracker.getId());
		} catch (Exception e) {
			map.put(ERRORS_MESSAGE, e.getMessage());
			return returnToPage(map);
		}
		
		if(!trackerService.checkPassword(bddTracker, inputTracker.getPassword())) {
			map.put(ERRORS_MESSAGE, "The tracker password is invalid.");
			map.put("urlToTrack", inputTracker.getUrlToTrack().getUrlString());
			map.put("selectedFrequency", inputTracker.getTrackingFrequency());
			map.put("textToTrack", inputTracker.getTextToTrack());
			map.put("name", inputTracker.getName());
			map.put("enable", inputTracker.isEnable());
			map.put("email", inputTracker.getEmail());
			return returnToPage(map);
		}
		
		if(null != request.getParameter("delete")) {
			// delete tracker
			trackerService.deleteTracker(bddTracker.getId());
			map = new ModelMap();
			map.put(INFOS_MESSAGE, "The tracker with id n°" + inputTracker.getId() + " has been deleted");
		} else {
			// update tracker
			trackerService.updateTracker(inputTracker);
			map = new ModelMap();
			map.put(INFOS_MESSAGE, "The tracker with id n°" + inputTracker.getId() + " has been updated");
		}
		
		requestHelper.addGenericAttributes(map);
		return new ModelAndView("redirect:/managetracker", map);
	}
	
}
