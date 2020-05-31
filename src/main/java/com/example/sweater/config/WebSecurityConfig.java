package com.example.sweater.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;      // генерируется спрингом

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

////    deprecated - потому что нужен только для отладки (ничего нигде не хранит)
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
        auth.jdbcAuthentication()
                .dataSource(dataSource)                                     // чтобы менеджер мог ходить в БД и искать там роли
                .passwordEncoder(NoOpPasswordEncoder.getInstance())         // чтобы пароли не хранились в открытом виде
//                получаем жестко заданный системой список полей конкретного юзера
                .usersByUsernameQuery("select username, password, active from usr where username = ?")
//                получаем ролей пользователя
                .authoritiesByUsernameQuery("select u.username, ur.roles from usr as u inner join user_role as ur on u.id = ur.user_id where u.username = ?");
    }

}
