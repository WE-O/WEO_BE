package bside.weo.plant.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 프론트와 다른 도메인간 호출로 인한 CORS 에러 관련 설정
        // 추후 allowedOrigins 주소를 우리의 도메인 이름으로 변경해야함
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowCredentials(true);
    }
}
