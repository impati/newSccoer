package com.example.newscoccer;

import com.example.newscoccer.support.ApplicationContextProvider;
import com.example.newscoccer.support.PostData;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;
import java.io.IOException;

@SpringBootApplication
@EnableJpaAuditing
public class NewScoccerApplication {

    public static void main(String[] args) throws IOException {

        SpringApplication.run(NewScoccerApplication.class, args);

    }

    @Configuration
    @RequiredArgsConstructor
    static class Config {
        private final EntityManager em;
        @Bean
        public JPAQueryFactory jpaQueryFactory() {
            return new JPAQueryFactory(em);
        }
    }

}
