package com.baeldung.lss.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class LssSecurityConfig extends WebSecurityConfigurerAdapter {

    public LssSecurityConfig() {
        super();
    }

    //

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { // @formatter:off 
        auth.
            inMemoryAuthentication()
            .withUser("user").password("pass").roles("USER").and()
            .withUser("admin").password("pass").roles("ADMIN")
            ;
    } // @formatter:on

    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
        http
        .authorizeRequests()
            //.antMatchers("/secured").access("hasRole('USER')")
            //.antMatchers("/secured").access("hasAuthority('ROLE_ADMIN')")
            //.antMatchers("/secured").hasIpAddress("192.168.1.0/24")
            //.antMatchers("/secured").access("hasIpAddress('192.168.1.0/24')")
            //.antMatchers("/secured").access("hasIpAddress('::1')")
            //.antMatchers("/secured").anonymous()
            //.antMatchers("/secured").access("isAnonymous()")
            //.antMatchers("/secured").access("request.method == 'GET'")
            //.anyRequest().authenticated()
            .anyRequest().permitAll()


        .and()
        .formLogin().
            loginPage("/login").permitAll().
            loginProcessingUrl("/doLogin")

        .and()
        .logout().permitAll().logoutUrl("/logout")
        
        .and()
        .csrf().disable()
        ;
    }

}
