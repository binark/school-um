package com.binark.school.usermanagement.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(IamServerConfig.class)
@EnableTransactionManagement
public class SecurityConfig {

    @Autowired
    IamServerConfig config;

    @Autowired
    Environment environment;

    private static final String ADMIN = "admin";
    private static final String SUPER_ADMIN = "SUPER";
    private static final String USER = "user";

   // private final OAuth2AuthenticationFilter authenticationFilter;

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

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Configuration
    @Order(1)
    public static class DefaultSecurityConfig {

        @Autowired
        private JwtAuthConverter jwtAuthConverter;

        /**
         * Set Authentication token header name
         * @return
         */
        @Bean
        BearerTokenResolver tokenResolver() {
            DefaultBearerTokenResolver bearerTokenResolver = new DefaultBearerTokenResolver();
            bearerTokenResolver.setBearerTokenHeaderName("SKUM-AUTH");

            return bearerTokenResolver;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http.securityMatcher("/user/**")
                    .csrf(csrf -> csrf.disable())
                    .sessionManagement(session ->
                            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/public/**", "/auth/**").permitAll()
                            .requestMatchers("/user/**").authenticated()
                                    //.hasAnyRole(ADMIN, USER)
                          //  .anyRequest().authenticated()
                    )
                    .oauth2ResourceServer()
                    .bearerTokenResolver(tokenResolver())
                    .jwt()
                    .jwtAuthenticationConverter(jwtAuthConverter);

            return http.build();
        }
    }

    @Configuration
    @Order(2)
    public static class AdminSecurityConfig {

        @Autowired
        AdminAuthenticationProvider adminAuthenticationProvider;

        @Bean
        AuthenticationManager authenticationManager() {
            return new ProviderManager(adminAuthenticationProvider);
        }

        @Bean
        public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {

            http.securityMatcher("/admin/**")
                    .authenticationManager(authenticationManager())
                    .sessionManagement(session ->
                        session.maximumSessions(1)
                    )
                    .authorizeHttpRequests(auth -> auth
                               //     .hasAuthority(SUPER_ADMIN)
                            .anyRequest().authenticated()
                    )
                    .formLogin()
                    .loginPage("/auth/admin/login")
                    .and()
                    .logout(logout -> logout.logoutUrl("/admin/logout"));

            return http.build();
        }
    }
}
