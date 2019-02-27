package com.shuai.hehe.server.shiro;


import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    /**
     * 这是个自定义的认证类，继承子AuthorizingRealm，负责用户的认证和权限处理
     */
    @Bean
    public Realm realm() {
        TextConfigurationRealm realm = new TextConfigurationRealm();
        realm.setUserDefinitions("joe.coder=password,user\n" +
                "jill.coder=password,admin");

        realm.setRoleDefinitions("admin=read,write\n" +
                "user=read");
        realm.setCachingEnabled(true);
        return realm;

//        MyShiroRealm realm = new MyShiroRealm();
//        //realm.setCredentialsMatcher(hashedCredentialsMatcher());
//        return realm;
    }

//    /**
//     * 安全管理器
//     * 将realm加入securityManager
//     *
//     * @return
//     */
//    @Bean
//    public SecurityManager securityManager() {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealm(realm());
//        return securityManager;
//    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/api/shiro",  "anon");
        // all other paths require a logged in user
        chainDefinition.addPathDefinition("/**", "authc");
        return chainDefinition;
    }

//    /**
//     * shiro filter 工厂类
//     * 1.定义ShiroFilterFactoryBean
//     * 2.设置SecurityManager
//     * 3.配置拦截器
//     * 4.返回定义ShiroFilterFactoryBean
//     */
//    @Bean
//    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
//        //1
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//
//        //2
//        //注册securityManager
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        Map<String,String> map = new HashMap<String, String>();
//        //登出
//        map.put("/logout","logout");
//        //对所有用户认证
//        map.put("/**","authc");
//        //登录
//        shiroFilterFactoryBean.setLoginUrl("/login");
//        //首页
//        shiroFilterFactoryBean.setSuccessUrl("/index");
//        //错误页面，认证不通过跳转
//        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
//        return shiroFilterFactoryBean;
////        //3
////        // 拦截器+配置登录和登录成功之后的url
////        //LinkHashMap是有序的，shiro会根据添加的顺序进行拦截
////        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
////        //配置不会被拦截的连接  这里顺序判断
////        //anon，所有的url都可以匿名访问
////        //authc：所有url都必须认证通过才可以访问
////        //user，配置记住我或者认证通过才能访问
////        //logout，退出登录
////        filterChainDefinitionMap.put("/JQuery/**", "anon");
////        filterChainDefinitionMap.put("/js/**", "anon");
////        //配置退出过滤器
////        filterChainDefinitionMap.put("/example1", "anon");
////        filterChainDefinitionMap.put("/lxt", "anon");
////        filterChainDefinitionMap.put("/login", "authc");
////        filterChainDefinitionMap.put("/success", "anon");
////        filterChainDefinitionMap.put("/index", "anon");
////        filterChainDefinitionMap.put("/Register", "anon");
////        filterChainDefinitionMap.put("/logout", "logout");
////        //过滤连接自定义，从上往下顺序执行，所以用LinkHashMap /**放在最下边
////        filterChainDefinitionMap.put("/**", "authc");
////        //设置登录界面，如果不设置为寻找web根目录下的文件
////        shiroFilterFactoryBean.setLoginUrl("/lxt");
////        //设置登录成功后要跳转的连接
////        shiroFilterFactoryBean.setSuccessUrl("/success");
////        //设置登录未成功，也可以说无权限界面
////        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
////
////        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
////        System.out.println("shiro拦截工厂注入类成功");
////
////        //4
////        //返回
////        return shiroFilterFactoryBean;
//    }

    //加入注解的使用，不加入这个注解不生效
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }
}
