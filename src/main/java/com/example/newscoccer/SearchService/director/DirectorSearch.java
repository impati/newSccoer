package com.example.newscoccer.SearchService.director;

import com.example.newscoccer.domain.director.Director;

import java.util.List;

public interface DirectorSearch {
    List<Director> directorSearch (DirectorSearchRequest request);
}
