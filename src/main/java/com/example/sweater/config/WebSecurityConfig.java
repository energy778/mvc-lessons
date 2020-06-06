package com.example.sweater.config;

import com.example.sweater.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                включаем авторизацию: доступ всем к greeting странице, а к остальным страницам - только авторизованным пользователям
                .authorizeRequests()
                    .antMatchers("/", "/registration").permitAll()
                    .anyRequest().authenticated()
                .and()
//                указываем, что будет использоваться форма логина, путь к ней и права доступа к этой форме
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
//                аналогично с формой logout
                .and()
                    .logout()
                    .permitAll()
                    .logoutSuccessUrl("/");     // куда перенаправить после успешного логаута
    }

////    deprecated - нужен только для отладки (ничего нигде не хранит)
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());         // чтобы пароли не хранились в открытом виде
    }

}
