package com.example.newscoccer.SearchService.common;
/**
 * @param <P> request Dto
 * @param <R> response Dto
 */
public interface EntityLeagueInfo <P,R>{
    R leagueInfo(P id);
}
