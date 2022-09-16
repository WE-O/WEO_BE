package bside.weo.plant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class PlantApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlantApplication.class, args);
	}

}
