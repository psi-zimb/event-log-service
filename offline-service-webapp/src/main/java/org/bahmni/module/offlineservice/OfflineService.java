package org.bahmni.module.offlineservice;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = "org.bahmni.module.*")
@EnableTransactionManagement
public class OfflineService extends SpringBootServletInitializer {

    @RequestMapping("/")
    String home() {
        return "Bahmni offline service is up and running.";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(OfflineService.class, args);
    }

    @Bean
    public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf) {
        return hemf.getSessionFactory();
    }
}
