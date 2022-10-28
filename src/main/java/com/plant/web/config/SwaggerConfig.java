package com.plant.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
//@EnableSwagger2
public class SwaggerConfig {

    private final String BASEPACKGE = "com.plant.web.api.member.adapter.in.controller";

    private final String version = "/v1";

    private final String PATH = "/api" + version + "/**";

    private final String TITLE = "식식물물 API";

    @Bean
    public Docket api(){

        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant(PATH))
                .build()
                .apiInfo(apiInfo(TITLE, version));
    }

    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfoBuilder()
                .title(title)
                .description(TITLE + version)
                .version(version)
                .build();
    }
}
