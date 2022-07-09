package com.example.newscoccer;

import com.example.newscoccer.support.ApplicationContextProvider;
import com.example.newscoccer.support.PostData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;

@SpringBootApplication
@EnableJpaAuditing
public class NewScoccerApplication {

    public static void main(String[] args) throws IOException {

        SpringApplication.run(NewScoccerApplication.class, args);

    }

}
