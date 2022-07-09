package com.example.newscoccer.support;

import com.example.newscoccer.domain.Team;

import java.io.IOException;
import java.util.List;

@FunctionalInterface
public interface PlayerRegisterByFile {
    void doIt(String name, List<Team> teams) throws IOException;
}
