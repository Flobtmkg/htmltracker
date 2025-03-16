package flo.web.htmltracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HtmltrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HtmltrackerApplication.class, args);
	}

}
