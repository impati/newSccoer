package com.example.newscoccer.SearchService.common;

/**
 * @param <P> request Dto
 * @param <R> response Dto
 */
public interface SimpleInfo <P,R>{
    R simpleInfo(P req);
}
