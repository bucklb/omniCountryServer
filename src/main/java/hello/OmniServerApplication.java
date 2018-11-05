package hello;

import org.springframework.boot.SpringApplication;

// This stuff seemed needed for RESTful spring boot
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;




// Let Spring take the strain for restful aspect
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "hello")
public class OmniServerApplication {

	// This is about as much as seems to be needed to get things moving.
	public static void main(String[] args) {
		System.out.println("in MAIN and want to check what Travis CI does");
		SpringApplication.run(OmniServerApplication.class, args);
	}
}
