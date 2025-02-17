package vn.com.easyjob.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import vn.com.easyjob.jwt.JwtFilter;
import vn.com.easyjob.service.auth.AccountService;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@OpenAPIDefinition(
//        security = @SecurityRequirement(name = "bearer-key"),
        servers = {@Server(url = "/")}
)
public class SercurityConfig {
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private AccountService accountService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
//                                .requestMatchers("/api/auth/logout").hasAnyAuthority("ROLE_ADMIN", "ROLE_OWNER", "ROLE_USER")
//                                .requestMatchers("/api/auth/**").permitAll()
//                                .requestMatchers("/api/accounts/verifyEmail").permitAll()
//                                .requestMatchers("/api/*/*/admin/**").hasAuthority("ROLE_ADMIN")
//                                .requestMatchers("/**").permitAll()
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers("/**").permitAll()
//                                .requestMatchers("api/auth/sign-up", "api/auth/sign-in", "api/auth/forget-password").permitAll()
                                .anyRequest().authenticated()
                )
                //                .exceptionHandling(e -> {
//                    e.accessDeniedHandler(accessDeniedHandler());
//                    e.authenticationEntryPoint(authenticationEntryPoint());
//                } )
                .logout(LogoutConfigurer::permitAll)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(accountService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://localhost:3100");
        config.addAllowedOrigin("http://61.14.233.181:3000");
        config.addAllowedOrigin("https://61.14.233.181:3000");
        config.addAllowedOrigin("http://61.14.233.181:3100");
        config.addAllowedOrigin("https://61.14.233.181:3100");
        config.addAllowedOrigin("http://61.14.233.181:443");
        config.addAllowedOrigin("https://61.14.233.181:443");
        config.addAllowedOrigin("http://api.easyjob.io.vn");
        config.addAllowedOrigin("https://api.easyjob.io.vn");
        config.addAllowedOrigin("http://api.easyjob.io.vn:8080");
        config.addAllowedOrigin("https://api.easyjob.io.vn:8080");
        config.addAllowedOrigin("http://admin.easyjob.io.vn");
        config.addAllowedOrigin("https://admin.easyjob.io.vn");
        config.addAllowedOrigin("http://easyjob.io.vn");
        config.addAllowedOrigin("https://easyjob.io.vn");
        config.addAllowedOrigin("http://easyjob.io.vn:3000");
        config.addAllowedOrigin("https://easyjob.io.vn:3000");
        config.addAllowedOrigin("http://admin.easyjob.io.vn:3100");
        config.addAllowedOrigin("https://admin.easyjob.io.vn:3100");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true); // Cho phép gửi cookie cùng với request

        // Test data
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    //config security swagger
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
