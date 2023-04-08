package com.binark.school.usermanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(IamServerConfig.class)
public class SecurityConfig {

    @Autowired
    IamServerConfig config;

    @Autowired
    Environment environment;

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build());

        return manager;
    }
        @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }

   // @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      //  http.csrf().disable();
        http.authorizeHttpRequests(auth -> auth
               // .requestMatchers("/private").authenticated()
                .anyRequest().permitAll()
        );
                //.oauth2Login();

        return http.build();
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    String test() {
        System.out.println("**************************************************");
        System.out.println("config = " + config);
        System.out.println("**************************************************");

        System.out.println("**************************************************");
        System.out.println("env = " + environment.getProperty("iam.uri"));
        System.out.println("**************************************************");
        return "djkgdkjg";
    }
}
