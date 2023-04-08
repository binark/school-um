package com.binark.school.usermanagement.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("iam")
//@ConfigurationProperties
//@Configuration
@Data
@ToString
public class IamServerConfig {

   // @Value("${application.name}")
    private String uri;

   // @Value("iamrealm")
    private String realm;

}
