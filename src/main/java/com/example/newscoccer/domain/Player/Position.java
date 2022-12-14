package com.example.newscoccer.domain.Player;

public enum Position {
    ST
    ,LF,RF,
    CF,

    AM,
    LM,CM,RM,
    DM,

    LB,LWB,CB ,RB, RWB,

    GK;

    public static int STRIKER_POSITION_COUNT = 4;
    public static int MIDFIELDER_POSITION_COUNT = 5;
    public static int DEFENDER_POSITION_COUNT = 5;
    public static int GOALKEEPER_POSITION_COUNT = 1;
}
/**
 * 0 - 100 반응속도,위치 센스
 *  공격수
 *  60 - 85 가속력 ,
 *  55 - 75 속력 ,볼컨
 *  50 - 70 속력 , 가속력 ,몸싸움 ,밸런스
 *  40 - 70 점프력 ,패스, 드리블 , 결정력 ,슛파워
 *  40 - 60 몸싸움 , 체력 ,적극성 ,밸런스 ,크로스
 *  30 - 60 점프력 ,드래블 ,결정력
 *  30 - 50 롱패스
 *  20 - 60 헤딩
 *  20 - 50 중거리슛
 *  10 - 50 수비
 *  0 - 10  골기퍼
 *
 *
 * 미드필더
 *  60 - 85 가속력
 *  50 - 75 몸싸움 , 체력
 *  50 - 65
 *  50 - 70 적극성 ,크로스
 *  40 - 70 볼컨트롤 , 패스 , 수비력
 *  40 - 60 몸싸움  ,점프력 ,밸런스 , 롱패스 ,중거리슛
 *  30 - 60  드리블
 *  30 - 50  가속력 ,체력
 *  20 - 60 슛파워
 *  20 - 50 크로스 ,결정력 , 수비력
 *  10 - 50
 *  0 - 10  골기퍼
 *
 *  수비수
 *
 *     60 - 85 몸싸움
 *  *  50 - 75
 *  *  50 - 65
 *  *  50 - 70  체력 , 적극성
 *  *  40 - 70  가속력 점프력
 *  *  40 - 60  밸런스 , 패스
 *  *  30 - 60  몸싸움
 *  *  30 - 50
 *  *  20 - 60
 *  *  20 - 50 가속력
 *  *  10 - 50
 *  *  0 - 10  골기퍼
 *
 *
 *
 */