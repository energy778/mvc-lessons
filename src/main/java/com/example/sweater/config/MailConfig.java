package com.example.sweater.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.port}")
    private int port;           // тип именно int !!

    @Value("${spring.mail.protocol}")
    private String protocol;

    @Value("${mail.debug}")
    private String debug;           // тип именно String, не смотря на то, что в свойствах он явно имеет булев тип !!

    @Bean
    public JavaMailSender getMailSender(){

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties mailProperties = mailSender.getJavaMailProperties();
        mailProperties.setProperty("mail.transport.protocol", protocol);
        mailProperties.setProperty("mail.debug", debug);        // не для продакшена. но позволяет посмотреть доп информацию, когда что-то пошло не так

        return mailSender;

    }

}
