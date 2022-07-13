package com.example.newscoccer.testConfig;

import com.example.newscoccer.RegisterService.director.DirectorUpdate;
import com.example.newscoccer.springDataJpa.DirectorRepository;
import com.example.newscoccer.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DirectorConfig {
    private final DirectorRepository directorRepository;
    private final TeamRepository teamRepository;
    @Bean
    public DirectorUpdate directorUpdate(){
        return new DirectorUpdate(directorRepository,teamRepository);
    }
}
