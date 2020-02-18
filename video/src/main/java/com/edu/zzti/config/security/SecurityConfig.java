package com.edu.zzti.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //  启用方法级别的权限认证
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SpLyUserDetailsService spLyUserDetailsService;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //  允许所有用户访问"/"和"/index.html"
        http//关闭跨站请求伪造
                .csrf().disable()
                .authorizeRequests()
                //api要允许匿名访问
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(
                        "/",
                        "/xl/**",
                        "/index.html",
                        "/note.html",
                        "/registerInput.html",
                        "/error.html",
                        "/favicon.ico",
                        "/sendEmail.html"
                 )
                .permitAll()
                .anyRequest().authenticated()   // 其他地址的访问均需验证权限
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .defaultSuccessUrl("/manager")
                .failureUrl("/error").permitAll()
                .and()
                .logout().permitAll()
                .logoutSuccessUrl("/login")
                .and()
                .headers().frameOptions().disable().and()
                // 禁用缓存
                .headers().cacheControl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(spLyUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers(
                "/static/**",
                "/upload/**",
                "/logout.html",
                "/login.html",
                "/register.html"
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}