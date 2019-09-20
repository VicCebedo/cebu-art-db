/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cebedo.vic.artdb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author Vic Cebedo <cebedo.vii@gmail.com>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private WebAuthenticationProvider webAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(webAuthenticationProvider);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // TODO Enable CSRF.
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/logged-in/*").authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/process")
                .defaultSuccessUrl("/logged-in/home", true)
                .failureUrl("/login/fail")
                .and()
                .logout()
                .logoutUrl("/logged-in/logout")
                .deleteCookies("JSESSIONID");
    }
}
