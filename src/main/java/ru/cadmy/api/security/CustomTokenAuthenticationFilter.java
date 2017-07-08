package ru.cadmy.api.security;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import ru.cadmy.api.model.Token;
import ru.cadmy.api.service.TokenService;

/**
 * Created by Cadmy on 08.07.2017.
 */
@Slf4j
public class CustomTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private TokenService tokenService;

    public CustomTokenAuthenticationFilter(String defaultFilterProcessesUrl,
                                           TokenService tokenService) {
        super(defaultFilterProcessesUrl);
        this.tokenService = tokenService;
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(defaultFilterProcessesUrl));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String token = request.getHeader("token");
        Authentication userAuthenticationToken = parseToken(token);
        if (userAuthenticationToken == null) {
            throw new AuthenticationServiceException("Wrong authentication token");
        }
        return userAuthenticationToken;
    }

    private Authentication parseToken(String tokenString) {
        if (!Strings.isNullOrEmpty(tokenString)) {
            try {
                Token token = tokenService.getToken(tokenString);
                if (token != null && token.getValidDate().isAfter(LocalDateTime.now())) {
                    return new UsernamePasswordAuthenticationToken(token.getProfile(),
                            token.getProfile().getPassword(),
                            Lists.newArrayList(new SimpleGrantedAuthority("ROLE_ADMIN")));
                }
            } catch (Exception e) {
                log.error("Cannot authenticate user", e);
                return null;
            }
        }
        return null;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {
        setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                    throws IOException, ServletException {
                chain.doFilter(request, response);
            }
        });
        super.doFilter(req, res, chain);
    }
}

