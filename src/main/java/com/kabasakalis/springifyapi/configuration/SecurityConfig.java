package com.kabasakalis.springifyapi.configuration;

import com.kabasakalis.springifyapi.security.JwtAuthFilter;
import com.kabasakalis.springifyapi.security.JwtAuthenticationEntryPoint;
import com.kabasakalis.springifyapi.security.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.kabasakalis.springifyapi.configuration.SecurityConstants.ADMIN_PATHS;
import static com.kabasakalis.springifyapi.configuration.SecurityConstants.PERMIT_ALL_PATHS;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

@Autowired
private JwtAuthFilter jwtAuthFilter;

@Autowired
private JwtAuthenticationProvider jwtAuthenticationProvider;

@Autowired
private JwtAuthenticationEntryPoint jwtAuthEndPoint;

@Override
public void configure(AuthenticationManagerBuilder auth)  throws Exception {
    auth.authenticationProvider(jwtAuthenticationProvider);
}

@Override
protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().authorizeRequests()
            .antMatchers(PERMIT_ALL_PATHS).permitAll()
            .antMatchers(ADMIN_PATHS).hasAuthority("ADMIN")
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
                .authenticationEntryPoint(jwtAuthEndPoint);
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
