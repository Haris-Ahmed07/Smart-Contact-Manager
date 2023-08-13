package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class MyConfig extends WebSecurityConfigurerAdapter {

	
//	UserDetailsService bean to use it for the configuration class
    @Bean
    public UserDetailsService UserDetailsService() {
        return new UserDetailsServiceImpl();
    }

//    Encoder to use it in the configuration class to encrypt & decrypt password
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    DaoAuthenticationProvider create a object by configuring passwordEncoder & userDetailService
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.UserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());

        return daoAuthenticationProvider;
    }

//    Configuration method to configure the AuthenticationProvider
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(this.authenticationProvider());
    }

//    Configuration method to configure the Spring Security for Role-Based Authentication and FormLogin configuration
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER").antMatchers("/**").permitAll()
                .and().formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/user/dashboard")
                .and().csrf().disable();
    }


}






