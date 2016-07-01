package com.baeldung.lss.spring;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.baeldung.lss.model.User;
import com.baeldung.lss.persistence.UserRepository;

@EnableWebSecurity
public class LssSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    public LssSecurityConfig() {
        super();
    }

    //

    @PostConstruct
    private void saveTestUser() {
        final User user = new User();
        user.setEmail("test@email.com");
        user.setPassword("pass");
        userRepository.save(user);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {// @formatter:off
        // auth.authenticationProvider(customAuthenticationProvider);

        // final DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
        // daoAuthProvider.setUserDetailsService(userDetailsService);
        // auth.authenticationProvider(customAuthenticationProvider).authenticationProvider(daoAuthProvider);

        auth.authenticationProvider(daoAuthenticationProvider());
        auth.authenticationProvider(runAsAuthenticationProvider());
    } // @formatter:on

    @Override
    protected void configure(HttpSecurity http) throws Exception {// @formatter:off
        http.authorizeRequests()
            .antMatchers("/signup", "/user/register", "/registrationConfirm*", "/badUser*", "/js/**").permitAll()
            .anyRequest().authenticated()

            .and().formLogin().
                    loginPage("/login").permitAll().
                    loginProcessingUrl("/doLogin")

            .and().logout().permitAll().logoutUrl("/logout")

            .and().csrf().disable();
    } // @formatter:on

    @Bean
    public AuthenticationProvider runAsAuthenticationProvider() {
        final RunAsImplAuthenticationProvider authProvider = new RunAsImplAuthenticationProvider();
        authProvider.setKey("MyRunAsKey");
        return authProvider;
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

}
