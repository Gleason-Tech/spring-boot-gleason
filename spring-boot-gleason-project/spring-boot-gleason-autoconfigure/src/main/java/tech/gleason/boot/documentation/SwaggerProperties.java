package tech.gleason.boot.documentation;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Swagger API documentation.
 */
@Data
@ConfigurationProperties(prefix = "spring.gleason.documentation")
public class SwaggerProperties {

    private String applicationName;
    private String licenseName = "Apache 2.0";
}
