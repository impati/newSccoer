package com.example.newscoccer.RegisterService.round;

public interface ChampionsRoundGenerator {
    /**
     * @param season , roundSt(만들어야되는 roundSt)
     * 라운드
     *  16라운드를 만드는 경우
     *      *  시즌 0 인 경우 :
     *      *  16개팀을 내가 선정.
     *      *
     *      *  그렇지 않은 경우 :
     *      *  각 리그에서 4위안에 있는 팀을 선정
     *
     *
     *  8 , 4 라운드를 만드는 경우
     *
     *  결승 라운드를 만드는 경우
     */
    void generator(int season , int roundSt);
}
