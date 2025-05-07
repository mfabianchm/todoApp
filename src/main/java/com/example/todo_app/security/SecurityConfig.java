package com.example.todo_app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //Here the Autowired annotation injects the CustomUserDetailsService class, so we can
    //have access to its properties and methods.
//    This tells Spring:
//    “Find a bean of type CustomUserDetailsService in the Application Context and inject it here.”
        @Autowired
        CustomUserDetailsService customUserDetailsService;


    /*Returns a BCryptPasswordEncoder instance, which is an implementation of PasswordEncoder that
    uses the BCrypt hashing function to hash passwords.
    The @Bean annotation registers this method's return value (a PasswordEncoder instance) in the
    Spring Application Context, making it accessible for dependency injection elsewhere in your application.
    It's marked static, which is valid for a bean method — this just means it can be called without an
    instance of SecurityConfig, which sometimes helps with the order of bean creation.*/
        @Bean
        public static PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        //SecurityFilterChain registered in the SpringApplicationContext because of the use of @Bean
//        This method configures how Spring Security will handle web requests, login, logout, and CSRF protection.
//        It returns a SecurityFilterChain bean, which Spring Security uses to build the filter chain
//        that intercepts and secures HTTP requests.
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(registry->{
                        registry.requestMatchers("/register", "/home").permitAll()
                                .anyRequest().authenticated();
                        ;
                    })
                    .formLogin(httpSecurityFormLoginConfigurer -> {
                        httpSecurityFormLoginConfigurer
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/home", true)
                                .permitAll();
                    })
                    .logout(logout -> logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/login?logout")
                            .invalidateHttpSession(true)
                            .clearAuthentication(true)
                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                            .deleteCookies("JSESSIONID")
                            .permitAll()); // Ensure logout is permitted
            return http.build();
        }


        //Spring injects AuthenticationManagerBuilder just for the duration of this method call, so you can configure it.
        // It's not stored as a class field in this class (SecurityConfig).
         //We don’t (and can't) store AuthenticationManagerBuilder as a field.
        //You get access to it once, inside the @Autowired method, to customize authentication.

//    Why is it done this way?
//    AuthenticationManagerBuilder is part of the internal setup process of Spring Security.
//        It's not a bean you declare yourself — Spring creates and manages it behind the scenes.
//    By using @Autowired on a method, Spring gives you a chance to configure it before it finalizes the AuthenticationManager.
//    By using @Autowired on a method, Spring gives you a chance to configure it before it finalizes the AuthenticationManager.

    /*This method tells Spring Security:
       "When you're building the AuthenticationManager, use this CustomUserDetailsService to load
       user details, and use this PasswordEncoder (BCrypt) to compare passwords."*/
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        }
}
