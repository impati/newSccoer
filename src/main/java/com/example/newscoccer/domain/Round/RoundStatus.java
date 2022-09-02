package com.example.newscoccer.domain.Round;

public enum RoundStatus {
    YET,  // 처음 생성 되었을 떄 .
    ING,  // 경기 중
    PAIR, // 골-어시 기록 저장 전
    DONE  // 완전 종료
}