package net.codigo.bookingsystem.base.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


import static net.codigo.bookingsystem.base.entity.enums.Permission.*;
import static net.codigo.bookingsystem.base.entity.enums.Role.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  private static final String[] WHITE_LIST_URL = {
          "/api/v1/auth/**",
          "/v2/api-docs",
          "/v3/api-docs",
          "/v3/api-docs/**",
          "/swagger-resources",
          "/swagger-resources/**",
          "/configuration/ui",
          "/configuration/security",
          "/swagger-ui/**",
          "/webjars/**",
          "/swagger-ui.html"};

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req ->
                    req.requestMatchers(WHITE_LIST_URL)
                            .permitAll()
                            .requestMatchers("/api/v1/user/**").hasAnyRole(ADMIN.name(), USER.name())
                            .requestMatchers(GET, "/api/v1/user/**").hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                            .requestMatchers(POST, "/api/v1/user/**").hasAnyAuthority(ADMIN_CREATE.name(), USER_CREATE.name())
                            .requestMatchers(PUT, "/api/v1/user/**").hasAnyAuthority(ADMIN_UPDATE.name(), USER_UPDATE.name())
                            .requestMatchers(DELETE, "/api/v1/user/**").hasAnyAuthority(ADMIN_DELETE.name(), USER_DELETE.name())
                            .anyRequest()
                            .authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .logout(logout ->
                    logout.logoutUrl("/api/v1/auth/logout")
                            .addLogoutHandler(logoutHandler)
                            .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
            )
    ;

    return http.build();
  }

}
