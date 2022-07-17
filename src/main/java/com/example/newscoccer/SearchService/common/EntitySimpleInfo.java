package com.example.newscoccer.SearchService.common;

/**
 * @param <P> request Dto
 * @param <R> response Dto
 */
public interface EntitySimpleInfo<P,R>{
    R simpleInfo(P req);
}
