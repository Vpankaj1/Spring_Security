package com.learn.config;

import com.learn.services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void  configure(HttpSecurity http) throws Exception{
        http
                //.csrf().disable()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeRequests()
                //.antMatchers("/home","/login","/register").permitAll()
                //.antMatchers("/public/**").permitAll()
                .antMatchers("/signin").permitAll()
                .antMatchers("/public/**").hasRole("NORMAL")
                .antMatchers("/user/**").hasRole("admin")
                .anyRequest()
                .authenticated()
                .and()
                //.httpBasic();
                .formLogin()
                .loginPage("/signin")
                .loginProcessingUrl("/dologin")
                .defaultSuccessUrl("/users/");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       // auth.inMemoryAuthentication().withUser("john").password(this.passwordEncoder().encode("lajka")).roles("NORMAL");
        //auth.inMemoryAuthentication().withUser("roshni").password(this.passwordEncoder().encode("abc")).roles("ADMIN");
    auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }


    //ROLE - High Level Overview->NORMAL :READ
    //Authorty - permission->READ
    //ADMIN - READ,WRITE,UPDATE




    @Bean
    //public PasswordEncoder passwordEncoder(){
    public BCryptPasswordEncoder passwordEncoder(){
       // return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder(10);
    }
}
