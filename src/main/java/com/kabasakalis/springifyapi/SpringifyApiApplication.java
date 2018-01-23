package com.kabasakalis.springifyapi;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableEncryptableProperties
@SpringBootApplication
public class SpringifyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringifyApiApplication.class, args); }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }

	public static ConfigurableApplicationContext run(String[] args) {
		return SpringApplication.run(SpringifyApiApplication.class, args);
	}

}
