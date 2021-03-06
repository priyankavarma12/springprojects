package com.libraryService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.libraryService.constants.StringConstants.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket libraryApi(){
        return new Docket( DocumentationType.SWAGGER_2)
                .select()
                .apis( RequestHandlerSelectors.withClassAnnotation( RestController.class))
                .paths( PathSelectors.any())
                .build()
                .apiInfo(metaData())
                .tags(new Tag( LIBRARY_SWAGGER_TAG, LIBRARY_SWAGGER_TAG_DESC ));
    }


    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title(LIBRARY_SERVICE_REST_API)
                .description(SWAGGER_PROJECT_DESC)
                .contact( new Contact(AUTHOR, PROJECT_URL, AUTHOR_EMAIL))
                .license(APACHE_LICENSE)
                .licenseUrl(LICENSE_URL)
                .version(VERSION)
                .termsOfServiceUrl( TERMS_OF_SERVICE )
                .build();

    }
}
