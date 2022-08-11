package com.example.newscoccer.auto.lineUp;

/**
 *  자동 라인 업 저장 기능.
 *
 *  1. RoundLineUp -> 라인업을 등록할 수 있는 선수들을 가져옴 .
 *
 *
 *  2. 라인업 등록할 선수들을 결정
 *
 *  3. 2번의 선수들을 LineUpRegister 이용하여 등록.
 *
 *
 */
public interface AutoLineUp {
    void autoLineUp(Long roundId);
}
