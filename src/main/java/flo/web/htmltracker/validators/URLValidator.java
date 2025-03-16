package flo.web.htmltracker.validators;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.util.UriComponentsBuilder;

import flo.web.htmltracker.Entities.Url;

@Service
public class URLValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Url.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Url url = (Url) target;
		
		if(null == url || null == url.getUrlString() || "".equals(url.getUrlString())){
			errors.reject("Url can not be empty.");
		}
		
		try {
			UriComponentsBuilder.fromHttpUrl(URLDecoder.decode(url.getUrlString(), "UTF-8")).build();
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			errors.reject(e.getMessage());
		}
	}
}
