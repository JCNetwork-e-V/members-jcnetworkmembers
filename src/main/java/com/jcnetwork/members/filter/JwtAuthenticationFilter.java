package com.jcnetwork.members.filter;

import com.jcnetwork.members.security.model.User;
import com.jcnetwork.members.security.service.TokenService;
import com.jcnetwork.members.security.model.AccountRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public JwtAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeaderIsInvalit(authorizationHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken token = createToken(authorizationHeader);

        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);
    }

    private boolean authorizationHeaderIsInvalit(String authorizationHeader) {
        return authorizationHeader == null || !authorizationHeader.startsWith("Bearer ");
    }

    private UsernamePasswordAuthenticationToken createToken(String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        User user = tokenService.parseToken(token);

        List<GrantedAuthority> authorities = new ArrayList<>();

        if(user.getRoles() != null) {
            for (AccountRole role : user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            }
        }

        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }
}
