package tn.esprit.bazaar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"tn.esprit.bazaar.serviceImpl", "tn.esprit.bazaar.config","tn.esprit.bazaar.controller","tn.esprit.bazaar.repository","tn.esprit.bazaar.entities","tn.esprit.bazaar.exceptions","tn.esprit.bazaar.dto","tn.esprit.bazaar.service"})
public class BazaarApplication {

	public static void main(String[] args) {
		SpringApplication.run(BazaarApplication.class, args);
	}

}
