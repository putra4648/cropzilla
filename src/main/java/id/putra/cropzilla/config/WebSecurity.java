package id.putra.cropzilla.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/static/**", "/webjars/**").permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2Login(oauth2 -> {
                    oauth2.loginPage("/oauth2/authorization/keycloak").defaultSuccessUrl("/menu", true);
                })
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // Redirects to the root URL on successful logout
                        .invalidateHttpSession(true) // Invalidates session to clear session data
                        .clearAuthentication(true) // Clears authentication details
                        .deleteCookies("JSESSIONID") // Deletes the session cookie
                );
        return http.build();
    }
}
