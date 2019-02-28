//package com.nonono.test._springboot.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                    .antMatchers("/", "/login").permitAll()     //设置对/和/login不进行拦截
//                    //.anyRequest().authenticated()
//                    .and()
//                .formLogin()
//                    .loginPage("/login")                                    //设置登录访问页面
//                    .successForwardUrl("/chat")                             //登录成功后转向/chat路径
//                    .permitAll()
//                    .and()
//                .logout()
//                    .permitAll();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //在内存中配置两个用户
//        auth
//                .inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("wyf").password(new BCryptPasswordEncoder().encode("wyf")).roles("USER")
//                .and()
//                .withUser("wisely").password(new BCryptPasswordEncoder().encode("wisely")).roles("USER")
//                .and()
//                .withUser("asika").password(new BCryptPasswordEncoder().encode("asika")).roles("USER")
//                .and()
//                .withUser("aoi").password(new BCryptPasswordEncoder().encode("aoi")).roles("USER");
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        //不拦截静态资源
//        web.ignoring().antMatchers("/resoures/static/**");
//    }
//}
