package com.jcnetwork.members.security.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {

    @Value("${azure.activedirectory.tenant-id}")
    String azureTenantId;

    @Value("${azure.activedirectory.logout-url}")
    String azureLogoutUrl;

    @Value("${security.logoutRedirect}")
    String logoutRedirect;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {

        azureLogoutUrl = azureLogoutUrl.replace("{TENANT-ID}", azureTenantId);
        azureLogoutUrl =
                azureLogoutUrl +
                request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + logoutRedirect;

        Object userPrincipal = authentication.getPrincipal();

        if (userPrincipal instanceof DefaultOidcUser) {
            response.sendRedirect(azureLogoutUrl);
        } else {
            response.sendRedirect(logoutRedirect);
        }
    }
}