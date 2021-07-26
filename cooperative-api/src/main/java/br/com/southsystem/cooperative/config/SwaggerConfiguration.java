package br.com.southsystem.cooperative.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration extends WebMvcConfigurationSupport {

    @Bean
    public Docket apiDocketV2() {
        ApiInfoBuilder info = new ApiInfoBuilder().title("Cooperative API v2").description("Cooperative Session API: adicionado a essa versoa o consumo de api externa para validacao de dados do usuario");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(info.version("2.0").build())
                .groupName("cooperative-api-latest")
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.southsystem.cooperative"))
                .paths(PathSelectors.ant("/api/v2/**"))
                .build();
    }

    @Bean
    public Docket apiDocket() {
        ApiInfoBuilder info = new ApiInfoBuilder().title("Cooperative API v1").description("Cooperative Session API: permite criacao de pautas e votacao");

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(info.version("1.0").build())
                .groupName("cooperative-api-v1.0").select()
                .apis(RequestHandlerSelectors.basePackage("br.com.southsystem.cooperative"))
                .paths(PathSelectors.ant("/api/v1/**")).build();
    }

    @Bean
    public Docket apiDocketUsers() {
        ApiInfoBuilder info = new ApiInfoBuilder().title("Users API").description("Users API v1: endpoints para fim de testes");

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(info.version("users-api-v1.0").build())
                .groupName("users-api-v1.0").select()
                .apis(RequestHandlerSelectors.basePackage("br.com.southsystem.cooperative.mock"))
                .paths(PathSelectors.ant("/users/**")).build();
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
