package com.lambdaschool.shoppingcart.config;

import com.lambdaschool.shoppingcart.services.HelperFunctions;
import com.lambdaschool.shoppingcart.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter
{
    @Autowired
    private HelperFunctions helperFunctions;

    @Autowired
    private UserService userService;

    private static final String RESOURCE_ID = "resource_id";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception
    {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception
    {


        http.authorizeRequests()
            .antMatchers("/",
                "/h2-console/**",
                "/swagger-resources/**",
                "/swagger-resource/**",
                "/swagger-ui.html",
                "/v2/api-docs",
                "/webjars/**",
                "/createnewuser")
            .permitAll()
            .antMatchers(HttpMethod.POST, "/users/user").hasAnyRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/users/user/{id}").hasAnyRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/users/user/{id}").hasAnyRole("ADMIN")
            .antMatchers(HttpMethod.GET, "/users/user/name/{userName}").hasAnyRole("ADMIN")
            .antMatchers(HttpMethod.GET, "/users/user/name/like/{userName}").hasAnyRole("ADMIN")
            .antMatchers(HttpMethod.GET, "/users/users").hasAnyRole("ADMIN")
            .antMatchers("/roles/**").hasAnyRole("ADMIN")
            .antMatchers("/products/**").hasAnyRole("ADMIN")
            .antMatchers("/users/**", "/carts/**", "/oauth/revoke-token", "/logout").authenticated()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(new OAuth2AccessDeniedHandler());


        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.logout()
            .disable();
    }
}

