package flo.web.htmltracker.controllers.helpers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import flo.web.htmltracker.GenericProperties;

@Service
public class RequestHelper {
	
	private static final String APP_VERSION = "appVersion";
	
	@Autowired
	private GenericProperties genericProperties;
	
	
	public void addGenericAttributes(ModelMap map) {
		map.addAttribute(APP_VERSION, genericProperties.getHtmltrackerVersion());
	}
	
	public void addGenericAttributes(Map<String, Object> paramMap) {
		paramMap.put(APP_VERSION, genericProperties.getHtmltrackerVersion());
	}

}
