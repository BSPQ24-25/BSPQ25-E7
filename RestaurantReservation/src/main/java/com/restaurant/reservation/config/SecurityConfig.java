package com.restaurant.reservation.config;

import com.restaurant.reservation.repository.UserRepository;
import com.restaurant.reservation.security.CustomAuthenticationSuccessHandler;
import com.restaurant.reservation.security.CustomUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;
    private final CustomAuthenticationSuccessHandler successHandler;

    public SecurityConfig(UserRepository userRepository, CustomAuthenticationSuccessHandler successHandler) {
        this.userRepository = userRepository;
        this.successHandler = successHandler;
    }
    

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                            "/", 
                            "/index.html", 
                            "/login.html", 
                            "/register.html",
                            "/css/**", 
                            "/js/**", 
                            "/images/**",
                            "/api/auth/register", 
                            "/api/auth/login"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login.html") // Página de login personalizada
                        .successHandler(successHandler) // AQUI USAMOS nuestro handler
                        .permitAll()
                )
                .build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                    "/css/**", 
                    "/js/**", 
                    "/images/**", 
                    "/index.html", 
                    "/login.html", 
                    "/register.html"
                );
    }
}
