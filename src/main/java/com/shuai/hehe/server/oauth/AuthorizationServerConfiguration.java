package com.shuai.hehe.server.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;
//    @Autowired
//    AuthenticationManagerBuilder authenticationManager;

    public AuthorizationServerConfiguration() {
        super();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
//                .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                ;
        //允许表单认证
        // security.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient("ClientId")
                .secret("secret")
                .authorizedGrantTypes("authorization_code","password")
                .scopes("user_info")
                .autoApprove(true)
                .accessTokenValiditySeconds(24*60*60);

        //配置两个客户端,一个用于password认证一个用于client认证
//        clients.inMemory().withClient("client_1")
//                .authorizedGrantTypes("client_credentials", "refresh_token")
//                .scopes("select")
//                .authorities("client")
//        .accessTokenValiditySeconds(3000);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);


        //TODO:这个的作用？
        endpoints.authenticationManager(authenticationManager);

        // Workaround for https://github.com/spring-projects/spring-boot/issues/1801
//        endpoints.authenticationManager(new AuthenticationManager() {
//            @Override
//            public Authentication authenticate(Authentication authentication)
//                    throws AuthenticationException {
//                return authenticationManager.getOrBuild().authenticate(authentication);
//            }
//        });
    }
}
