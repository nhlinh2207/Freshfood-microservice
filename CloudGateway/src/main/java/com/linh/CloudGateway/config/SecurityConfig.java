package com.linh.CloudGateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity){
//          httpSecurity
//                  .csrf().disable()
//                  .authorizeExchange(exchange -> exchange
//                          .pathMatchers("/eureka/**")
//                          .permitAll()
//                          .anyExchange()
//                          .authenticated())
//                  .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
//          return httpSecurity.build();

        httpSecurity
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(
                        "/eureke/**",
                        "/actuator/**",
                        "/user/login"
                ).permitAll()
                .and()
                .authorizeExchange()
                .anyExchange()
                .authenticated()
                .and()
                .oauth2Login(Customizer.withDefaults())
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
        return httpSecurity.build();
    }
}
