package ru.cadmy.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.util.Assert;

import ru.cadmy.api.security.CustomTokenAuthenticationFilter;
import ru.cadmy.api.security.RestAuthenticationEntryPoint;
import ru.cadmy.api.service.TokenService;

/**
 * Created by ssemenov on 28.06.17.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private TokenService tokenService;


    @Autowired
    public SecurityConfiguration(TokenService tokenService) {
        Assert.notNull(tokenService, "TokenService cannot be null!");
        this.tokenService = tokenService;
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        "/webjars/**", "/v2/api-docs/**", "/configuration/ui/**", "/swagger-resources/**",
                        "/configuration/security/**", "/swagger-ui.html/**", "/swagger-ui.html#/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        http.csrf().disable().addFilterBefore(new CustomTokenAuthenticationFilter("/api/**", tokenService),
                AnonymousAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint());
    }

}


