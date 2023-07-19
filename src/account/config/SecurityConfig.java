package account.config;

import account.business.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    CredentialsService credentialsService;

    @Bean
    SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint) // Handle auth error
                .and()
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and()
                .authorizeHttpRequests()// manage access
                .requestMatchers(HttpMethod.POST, "/api/auth/signup/**").permitAll()
                .requestMatchers(HttpMethod.POST, "api/auth/changepass/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/actuator/shutdown/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/acct/payments/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/acct/payments/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/empl/payment/**").authenticated()
                .anyRequest().authenticated()
                // other matchers
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();// no session
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(credentialsService);
        return authenticationManagerBuilder.build();
    }
}
