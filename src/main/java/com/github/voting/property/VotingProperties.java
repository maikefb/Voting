package com.github.voting.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(value = "voting.common.api")
public class VotingProperties {

    private SwaggerProperties swagger;

    @Data
    public static class SwaggerProperties {

        private String appName;

        private String appVersion;

        private String appDescription;

        private String appHost;

        private String basePackage;
    }
}
