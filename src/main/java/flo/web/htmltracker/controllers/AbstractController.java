package flo.web.htmltracker.controllers;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import flo.web.htmltracker.controllers.helpers.RequestHelper;

@Controller
public abstract class AbstractController {

	public static final String ERRORS_MESSAGE = "errors";
	public static final String INFOS_MESSAGE = "infos";
	
	@Autowired
	private RequestHelper requestHelper;
	
	public abstract ModelAndView init(ModelMap map, String infos);
	
	protected ModelAndView returnToPage(Map<String, Object> paramMap) {
		ModelMap model = new ModelMap();
		model.addAllAttributes(paramMap);
		requestHelper.addGenericAttributes(model);
		return init(model, null);
	}
	
	
	protected ModelAndView returnToPage(Errors erreur, Map<String, Object> paramMap) {
		paramMap.put(ERRORS_MESSAGE, erreur.getAllErrors().stream().map(error -> error.getCode()).collect(Collectors.toList()));
		requestHelper.addGenericAttributes(paramMap);
		return returnToPage(paramMap);
	}
}
