package flo.web.htmltracker.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import flo.web.htmltracker.Entities.Url;
import flo.web.htmltracker.services.TrackerService;
import flo.web.htmltracker.validators.URLValidator;

@CrossOrigin(origins = "${allow-origins}")
@RestController
public class HTMLCodeController {
	
	@Autowired
	private URLValidator urlValidator;
	
	@Autowired
	private TrackerService trackerService;
	
	@PostMapping("/checkurl")
	public String testLinkContent(@RequestBody Url url, Errors errors) {
		
		urlValidator.validate(url, errors);
		if(errors.hasErrors()) {
			return null;
		}
		
		String responseString = null;
		
		try {
			responseString = trackerService.findHTMLCodeFromURL(url);
		} catch (Exception e) {
			return null;
		}
		return responseString;
	}

}
