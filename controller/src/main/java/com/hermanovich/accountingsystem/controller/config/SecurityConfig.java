package com.hermanovich.accountingsystem.controller.config;

import com.hermanovich.accountingsystem.common.exception.ApplicationException;
import com.hermanovich.accountingsystem.controller.security.filter.SecurityFilter;
import com.hermanovich.accountingsystem.service.security.repository.TokenRepository;
import com.hermanovich.accountingsystem.controller.security.util.TokenHelper;
import com.hermanovich.accountingsystem.service.user.UserService;
import com.hermanovich.accountingsystem.util.MessageForUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final TokenHelper tokenHelper;
    private final TokenRepository tokenRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() {
        try {
            return super.authenticationManagerBean();
        } catch (Exception e) {
            throw new ApplicationException(MessageForUser.MESSAGE_AUTHENTICATION_EXCEPTION.get());
        }
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(this.userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/authorization/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .accessDeniedHandler((httpServletRequest, httpServletResponse, e) ->
                        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN))
                .and()
                .addFilter(new SecurityFilter(userService, tokenHelper, authenticationManager(), tokenRepository))
                .headers().cacheControl();
    }
}
