package com.example.newscoccer.SearchService.common;

/**
 *
 * 감독 ,선수 , 팀의 가장 기본적인 기능을 제공 .
 * @param <P> request Dto
 * @param <R> response Dto
 */
public interface EntitySimpleInfo<P,R>{
    R simpleInfo(P req);
}
