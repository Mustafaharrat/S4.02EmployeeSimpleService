package employee.sawgger.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


//http://localhost:8080/h2-console/
//http://localhost:8080/swagger-ui/index.html
@Configuration
public class SwaggerConfiguration {
	
	   @Bean
public OpenAPI customOpenAPI() {
return new OpenAPI().info(new Info().title("SpringShop API"));}

}
