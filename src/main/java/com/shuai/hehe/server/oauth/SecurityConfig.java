package com.shuai.hehe.server.oauth;

import com.google.gson.Gson;
import com.shuai.hehe.server.api.LoginController;
import com.shuai.hehe.server.response.ErrorCode;
import com.shuai.hehe.server.response.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * 安全配置
 */
@Configuration
//@EnableWebSecurity(debug = true)
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().
//        auth.inMemoryAuthentication()
//                .withUser("user_1").password("123456").authorities("USER")
//                .and()
//                .withUser("user_2").password("123456").authorities("USER");

//        auth
//                .inMemoryAuthentication()
//                .withUser("aa")
//                .password("aa")
//                .roles("USER");

        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new Md5PasswordEncoder());

//        auth.jdbcAuthentication().dataSource(dataSource).withUser("dave")
//                .password("secret").roles("USER");
    }

    /**
     * api接口安全配置
     */
    @Configuration
    @Order(1)
    public static class ApiSecurityAdapter extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            super.configure(resources);
            resources.stateless(false);
//        resources
//                .accessDeniedHandler(new OAuth2AccessDeniedHandler() {
//                    @Override
//                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
//                        //super.handle(request, response, authException);
//
//                        Gson gson = new Gson();
//                        ResponseInfo<LoginController.TokenInfo> result = new ResponseInfo<LoginController.TokenInfo>(ErrorCode.ERROR_ACCESS_DENY);
//                        String json = gson.toJson(result);
//                        response.setContentType("application/json; charset=UTF-8");
//                        response.getWriter().print(json);
//                    }
//                })
//                .authenticationEntryPoint(new OAuth2AuthenticationEntryPoint() {
//                    @Override
//                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//                        //super.commence(request, response, authException);
//
//                        Gson gson = new Gson();
//                        ResponseInfo<LoginController.TokenInfo> result = new ResponseInfo<LoginController.TokenInfo>(ErrorCode.ERROR_ACCESS_DENY);
//                        String json = gson.toJson(result);
//                        response.setContentType("application/json; charset=UTF-8");
//                        response.getWriter().print(json);
//                    }
//                });
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/**").authenticated();
//                .authorizeRequests()
//                .anyRequest().permitAll();
//            http.cors();

            http.antMatcher("/api/**")
                    .authorizeRequests()
                    .antMatchers(
                            "/api/login",
                            "/api/sendVerificationCode",
                            "/api/registerByPhone",
                            "/api/getVideoList",
                            "/api/getVideoDetail",
                            "/api/getAlbumList",
                            "/api/getAlbumPics",
                            "/api/getCommentList"
                    ).permitAll()
                    .antMatchers("/api/**").authenticated()
            ;

            http.csrf().disable();

            http.exceptionHandling()
                    .accessDeniedHandler(new OAuth2AccessDeniedHandler() {
                        @Override
                        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
                            //super.handle(request, response, authException);

                            Gson gson = new Gson();
                            ResponseInfo<LoginController.TokenInfo> result = new ResponseInfo<LoginController.TokenInfo>(ErrorCode.ERROR_ACCESS_DENY);
                            String json = gson.toJson(result);
                            response.setContentType("application/json; charset=UTF-8");
                            response.getWriter().print(json);
                        }
                    })
                    .authenticationEntryPoint(new OAuth2AuthenticationEntryPoint() {
                        @Override
                        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                            //super.commence(request, response, authException);

                            Gson gson = new Gson();
                            ResponseInfo<LoginController.TokenInfo> result = new ResponseInfo<LoginController.TokenInfo>(ErrorCode.ERROR_ACCESS_DENY);
                            String json = gson.toJson(result);
                            response.setContentType("application/json; charset=UTF-8");
                            response.getWriter().print(json);
                        }
                    });
        }


//    @Bean
//    AccessDeniedHandler accessDeniedHandler() {
//        AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
//        accessDeniedHandler.setErrorPage("/securityException/accessDenied");
//        return accessDeniedHandler;
//    }
//
//    @Bean
//    AuthenticationSuccessHandler authenticationSuccessHandler(){
//        SimpleUrlAuthenticationSuccessHandler handler=new SimpleUrlAuthenticationSuccessHandler();
//        return handler;
//    }
    }

    /**
     * web页安全配置
     */
    @Configuration
    @EnableResourceServer
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        //    @Autowired
//    private AuthenticationManager authenticationManager;
        @Override
        public void configure(WebSecurity web) throws Exception {
            super.configure(web);
            web.ignoring().antMatchers("/css/**", "/js/**", "/static/**", "/img/**");
        }


        @Override
        public void configure(HttpSecurity http) throws Exception {
//        super.configure(http);

//            http.cors();
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and().authorizeRequests()
                    .antMatchers("/", "/index.html**", "/index.html/**", "/login**", "/mymanager**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage("/login").permitAll()
                    //.defaultSuccessUrl("/")
                    .failureUrl("/login.html?error=true")
                    .and()
                    .logout().logoutSuccessUrl("/login");
            ;

            http.csrf().disable();

            http.exceptionHandling()
//                .accessDeniedHandler(accessDeniedHandler)
                    .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login.html"));

//
////        http
//                // Since we want the protected resources to be accessible in the UI as well we need
//                // session creation to be allowed (it's disabled by default in 2.0.6)
////                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
////                .and()
////                .authorizeRequests().antMatchers("/**").authenticated()
//                //.authorizeRequests().anyRequest().permitAll()
////                .and()
////                .anonymous()
////                .and().authorizeRequests()
////                .antMatchers("/oauth/**").permitAll()
////                .and()
////                .authorizeRequests()
//////                    .antMatchers("/product/**").access("#oauth2.hasScope('select') and hasRole('ROLE_USER')")
////                .antMatchers("/order/**").authenticated()
////                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());;//配置order访问控制，必须认证过后才可以访问
//        ;
//
//        http.authorizeRequests()
////                .antMatchers("/user_list","/login", "/oauth/authorize").permitAll()
//                .anyRequest().authenticated()
//        .and().formLogin()
//        ;
        }


    }
}
