package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.director.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director,Long> , DirectorRepositoryQuerydsl {

}
