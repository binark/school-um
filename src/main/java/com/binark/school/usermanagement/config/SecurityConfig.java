package com.binark.school.usermanagement.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(IamServerConfig.class)
public class SecurityConfig {

    @Autowired
    IamServerConfig config;

    @Autowired
    Environment environment;

    @Autowired
    AdminAuthenticationProvider adminAuthenticationProvider;

    private static final String ADMIN = "admin";
    private static final String SUPER_ADMIN = "SUPER";
    private static final String USER = "user";
    private final JwtAuthConverter jwtAuthConverter;
    private final OAuth2AuthenticationFilter authenticationFilter;

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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authenticationManager(authenticationManager());
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**", "/auth/**").permitAll()
              //  .requestMatchers("/admin").hasAuthority(SUPER_ADMIN)
                //.requestMatchers("/user").hasAnyRole(ADMIN, USER)
                .anyRequest().authenticated()
        )
                .formLogin()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        System.out.println("********************** login success ****************************");
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        System.out.println("************************ login failed ***********************");
                    }
                })
              //  .loginPage("/auth/admin/login");
                .and()
                .oauth2ResourceServer()
                .bearerTokenResolver(tokenResolver())
                .jwt()
                .jwtAuthenticationConverter(jwtAuthConverter);

    //    http.addFilterBefore(authenticationFilter, AbstractPreAuthenticatedProcessingFilter.class);
    //    http.addFilterBefore(adminAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    //    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

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

//    @Bean
//    AdminAuthenticationFilter adminAuthenticationFilter(HttpSecurity http) throws Exception {
//        return new AdminAuthenticationFilter(authenticationManager(http));
//    }

    @Bean
    AuthenticationManager authenticationManager() {
        return new ProviderManager(adminAuthenticationProvider);
    }


}
