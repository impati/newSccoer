package com.example.newscoccer.SearchService.common;
/**
 * @param <P> request Dto
 * @param <R> response Dto
 */
public interface EntityRecordInfo<P,R>{
    R recordInfo(P req);
}
