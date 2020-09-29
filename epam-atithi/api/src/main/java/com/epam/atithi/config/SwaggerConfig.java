package com.epam.atithi.config;

import com.epam.atithi.constants.SwaggerConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Class containing the configurations required for Swagger
 *
 * @author Spallya Omar
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket libraryApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())
                .tags(new Tag(SwaggerConstants.USER_SWAGGER_TAG, SwaggerConstants.USER_SWAGGER_TAG_DESC));
    }

    private ApiInfo metaData() {
        return new ApiInfo(
                SwaggerConstants.EPAM_ATITHI_REST_API,
                SwaggerConstants.SWAGGER_PROJECT_DESC,
                SwaggerConstants.VERSION,
                SwaggerConstants.TERMS_OF_SERVICE,
                new Contact(SwaggerConstants.AUTHOR, SwaggerConstants.PROJECT_URL, SwaggerConstants.AUTHOR_EMAIL),
                SwaggerConstants.APACHE_LICENSE,
                SwaggerConstants.LICENSE_URL);
    }
}