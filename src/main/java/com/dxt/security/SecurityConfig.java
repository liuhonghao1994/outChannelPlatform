package com.dxt.security;

import com.dxt.common.AppConstant;
import com.dxt.service.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全配置文件，主要是重写默认的认证方式和访问目录权限
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CacheManager cacheManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            //在此处应用自定义PasswordEncoder
            .passwordEncoder(new MyPasswordEncoder())
            .withUser(cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_ADMIN_USERNAME))
            .password(cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_ADMIN_PASSWORD)).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 免认证目录
                .antMatchers("/errors/**", "/assets/**", "/plugins/**", "/static/**", "/bootstrap/**","/view/**",
                        "/swagger-ui.html**", "/webjars/**", "/swagger-resources/**", "/api/**")
                // ADMIN角色可以访问/admin目录
                .permitAll().antMatchers("/admin/**").hasRole("ADMIN")
                // 自定义登录页为/login
                .anyRequest().authenticated().and().formLogin().loginPage("/login")
                .permitAll().and().logout().permitAll().and().csrf().disable();
    }

    static class MyPasswordEncoder implements PasswordEncoder {

        @Override
        public String encode(CharSequence arg0) {
            return arg0.toString();
        }

        @Override
        public boolean matches(CharSequence arg0, String arg1) {
            return arg1.equals(arg0.toString());
        }

    }

}
