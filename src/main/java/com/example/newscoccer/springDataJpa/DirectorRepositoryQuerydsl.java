package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.SearchService.director.DirectorSearchRequest;
import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.director.Director;

import java.util.List;

public interface DirectorRepositoryQuerydsl {
    List<Director> findDirectorList(String name , League league, Team team);
}
