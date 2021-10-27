package com.jcnetwork.members.config;

import com.jcnetwork.members.filter.JwtAuthenticationFilter;
import com.jcnetwork.members.security.handler.UserAuthenticationSuccessHandler;
import com.jcnetwork.members.security.handler.UserLogoutSuccessHandler;
import com.jcnetwork.members.security.service.UserService;
import com.jcnetwork.members.security.service.ApiTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.oidc.authentication.OidcAuthorizationCodeAuthenticationProvider;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    private final OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService;

    public WebSecurityConfiguration(OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService) {
        this.oidcUserService = oidcUserService;
    }

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        private final ApiTokenService apiTokenService;

        public ApiWebSecurityConfigurationAdapter(ApiTokenService apiTokenService) {
            this.apiTokenService = apiTokenService;
        }

        @Autowired
        private BCryptPasswordEncoder passwordEncoder;

        @Autowired
        private UserService apiMongoUserDetails;

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {

            auth
                    .userDetailsService(apiMongoUserDetails)
                    .passwordEncoder(passwordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .mvcMatcher("/api/**")
                    .csrf().disable()
                    .authorizeRequests()
                        .mvcMatchers("/api/generateToken").permitAll()
                        .mvcMatchers("/api/**").authenticated()
                    .and()
                        .addFilterBefore(new JwtAuthenticationFilter(apiTokenService),
                            UsernamePasswordAuthenticationFilter.class);
        }
    }

    @Configuration
    @Order(2)
    public static class StandardLoginWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Value("#{'${security.whitelistedUrls}'.split(',')}")
        private String[] whitelistedUrls;

        @Autowired
        private UserService mongoUserDetails;

        @Autowired
        private BCryptPasswordEncoder passwordEncoder;

        @Autowired
        private AuthenticationProvider activeDirectoryAuthenticationProvider;

        @Autowired
        private UserAuthenticationSuccessHandler userAuthenticationSuccessHandler;

        @Autowired
        private UserLogoutSuccessHandler userLogoutSuccessHandler;

        private final OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService;

        public StandardLoginWebSecurityConfigurationAdapter(OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService) {
            this.oidcUserService = oidcUserService;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {

            auth
                    .authenticationProvider(activeDirectoryAuthenticationProvider)
                    .userDetailsService(mongoUserDetails)
                    .passwordEncoder(passwordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .authorizeRequests()
                        .antMatchers(whitelistedUrls).permitAll()
                        .antMatchers("/consultancyRegistration").hasRole("CONSULTANCY_ADMIN")
                        .anyRequest().authenticated()
                    .and()
                        .formLogin()
                            .loginPage("/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .successHandler(userAuthenticationSuccessHandler)
                            .permitAll()
                    .and()
                        .logout()
                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                            .invalidateHttpSession(true)
                            .logoutSuccessHandler(userLogoutSuccessHandler)
                    .and()
                        .rememberMe()
                            .key("TESTKEY") //TODO change for real key
                    .and()
                        .oauth2Login()
                            .loginPage("/login")
                            .successHandler(userAuthenticationSuccessHandler)
                            .permitAll()
                            .userInfoEndpoint()
                            .oidcUserService(oidcUserService);
        }
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OAuth2AccessTokenResponseClient oAuth2AccessTokenResponseClient() {
        OAuth2AccessTokenResponseClient tokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
        return tokenResponseClient;
    }

    @Bean
    public AuthenticationProvider activeDirectoryAuthenticationProvider() {
        OidcAuthorizationCodeAuthenticationProvider provider = new OidcAuthorizationCodeAuthenticationProvider(
                oAuth2AccessTokenResponseClient(),
                oidcUserService);
        return provider;
    }
}