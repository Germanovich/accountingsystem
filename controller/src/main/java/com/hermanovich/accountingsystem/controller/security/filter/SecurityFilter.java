package com.hermanovich.accountingsystem.controller.security.filter;

import com.hermanovich.accountingsystem.service.security.repository.TokenRepository;
import com.hermanovich.accountingsystem.controller.security.util.TokenHelper;
import com.hermanovich.accountingsystem.service.user.UserService;
import com.hermanovich.accountingsystem.util.MessageForUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
public class SecurityFilter extends BasicAuthenticationFilter {

    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";

    private final UserService userService;
    private final TokenHelper tokenHelper;
    private final TokenRepository tokenRepository;

    public SecurityFilter(UserService userService,
                          TokenHelper tokenHelper,
                          AuthenticationManager authenticationManager,
                          TokenRepository tokenRepository) {
        super(authenticationManager);
        this.userService = userService;
        this.tokenHelper = tokenHelper;
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if (Boolean.TRUE.equals(tokenHelper.validateToken(token))) {
            String username = tokenHelper.getLoginFromToken(token);

            if (tokenRepository.getToken(username) == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                log.info(MessageForUser.MESSAGE_TOKEN_IS_INVALID.get());
                return;
            }
            UserDetails userDetails = userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null,
                    userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (bearer != null && bearer.startsWith(BEARER)) {
            return bearer.substring(BEARER.length());
        }
        return null;
    }
}
