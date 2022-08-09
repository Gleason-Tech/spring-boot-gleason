package tech.gleason.boot.documentation;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Configuration for Swagger API documentation support.
 *
 * @author Sean Gleason
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean({OpenAPI.class})
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration {

    private final SwaggerProperties swaggerProperties;

    /**
     * Default constructor.
     *
     * @param swaggerProperties - Loads properties for Swagger API documentation.
     */
    public SwaggerAutoConfiguration(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    /**
     * Provided OpenAPI implementation with basic API configuration.
     *
     * @param environment - Used to default Swagger API name to 'spring.application.name' if none is provided in the SwaggerProperties.
     * @return OpenAPI definition.
     */
    @Bean
    public OpenAPI openAPI(Environment environment) {
        if (isEmpty(this.swaggerProperties.getApplicationName())) {
            String applicationName = environment.getProperty("spring.application.name");
            if (isEmpty(applicationName)) {
                throw new InvalidConfigurationPropertyValueException("spring.gleason.documentation.application-name", null,
                        "This property is mandatory and fallback 'spring.application.name' is not set either.");
            }
            this.swaggerProperties.setApplicationName(applicationName);
        }
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title(swaggerProperties.getApplicationName())
                        .license(new License().name(swaggerProperties.getLicenseName())));
    }
}
