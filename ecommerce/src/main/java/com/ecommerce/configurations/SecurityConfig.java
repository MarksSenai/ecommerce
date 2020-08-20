package com.ecommerce.configurations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.security.CustomUserDetailsService;
import com.ecommerce.security.JwtAuthenticationEntryPoint;
import com.ecommerce.security.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final String [] PUBLIC_MATCHERS = {
            "/",
            "/**/favico.ico",
            "/**/**.ico",
            "/**/**.txt",
            "/**/**.woff2",
            "/**/**.svg",
            "/**/**.ttf",
            "/**/**.eot",
            "/**/**.woff",
            "/**/**.jpg",
            "/**/**.html",
            "/**/**.css",
            "/**/**.js"
    };

    @Override
    protected void configure(HttpSecurity http) throws  Exception {
        http.cors().and().csrf().disable().exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS)
                .permitAll()
                .antMatchers("/api/v1/auth/login/**")
                .permitAll()
                .antMatchers("/api/v1/company/createcompany/**")
                .permitAll()
                .antMatchers("/api/v1/user/createuser/**")
                .permitAll()
                .antMatchers("/api/v1/qrcode/createcode/**")
                .permitAll()
                .anyRequest()
                .authenticated();
        // Add our custom JWT security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
