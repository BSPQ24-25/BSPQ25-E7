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
        return new BCryptPasswordEncoder(); // Asegura hash de contraseñas
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable()) // Desactiva CSRF solo si es necesario
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", 
                    "/index", 
                    "/login", 
                    "/register", 
                    "/css/**", 
                    "/js/**", 
                    "/images/**", 
                    "/auth/register",
                        "/i18n/**" // Permite acceso a archivos de i18n

                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")              // Página de login
                .loginProcessingUrl("/login")          // URL que maneja POST de login
                .successHandler(successHandler)        // Redirige según rol
                .failureUrl("/login?error=true")  // Redirige si falla login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout") // Redirección tras logout
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
                "/images/**"
            );
    }
}
