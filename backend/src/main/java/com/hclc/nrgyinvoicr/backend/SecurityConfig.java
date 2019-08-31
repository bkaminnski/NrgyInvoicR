package com.hclc.nrgyinvoicr.backend;

import com.hclc.nrgyinvoicr.backend.users.control.AuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationFilter authenticationFilter;

    public SecurityConfig(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().httpBasic().disable().formLogin().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(authenticationFilter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/users/authentication").permitAll()
                .antMatchers("/", "/index.html", "/*.css", "/*.eot", "/*.ico", "/*.js", "/*.ttf", "/*.woff", "/*.woff2").permitAll()
                .anyRequest().authenticated();
    }
}
