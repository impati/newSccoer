package com.example.newscoccer.domain.Round;

/**
 * 라운드 기능의 최상위 인터페이스
 */
public interface RoundFunction {
    default boolean supports(Round round){return false;}
    default <T> T feature(RoundDto roundDto,   Class<T> returnType){return null;}
    default void feature(RoundDto roundDto){}
}
