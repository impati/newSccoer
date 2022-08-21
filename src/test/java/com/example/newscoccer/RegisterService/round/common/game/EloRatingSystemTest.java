package com.example.newscoccer.RegisterService.round.common.game;

import com.example.newscoccer.domain.record.MatchResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EloRatingSystemTest {

    private static int VI = 400;
    private static Integer K = 20;

    @Test
    @DisplayName("팀 레이팅 테스트")
    public void teamRating() throws Exception{

        // 같은 레이팅 이길시 + 10 , 0 , -10
        System.out.println("win AfterRating = " + afterRating(MatchResult.WIN,1500,1500));
        System.out.println("draw AfterRating = " + afterRating(MatchResult.DRAW,1500,1500));
        System.out.println("lose AfterRating = " + afterRating(MatchResult.LOSE,1500,1500));


        // 100점 차이 7 , -3 , -13
        System.out.println("win AfterRating = " + afterRating(MatchResult.WIN,1600,1500));
        System.out.println("draw AfterRating = " + afterRating(MatchResult.DRAW,1600,1500));
        System.out.println("lose AfterRating = " + afterRating(MatchResult.LOSE,1600,1500));

        // 200점 차이 4 , -6 , -16
        System.out.println("win AfterRating = " + afterRating(MatchResult.WIN,1700,1500));
        System.out.println("draw AfterRating = " + afterRating(MatchResult.DRAW,1700,1500));
        System.out.println("lose AfterRating = " + afterRating(MatchResult.LOSE,1700,1500));







    }


    private double afterRating(MatchResult matchResult , double myRating , double oppositeRating){
        double w = 0;
        double a = 10;
        double b = (oppositeRating - myRating) / VI;
        double we = 1 / (Math.pow(a,b) + 1);
        if(matchResult  == MatchResult.WIN) w = 1;
        else if(matchResult  == MatchResult.DRAW) w = 0.5;
        double r = myRating + K * (w - we);
        return r;
    }



    @Test
    @DisplayName("선수 레이팅 테스트")
    public void playerRatingTest() throws Exception{


        System.out.println("win " +afterRatingForPlayer(MatchResult.WIN,1500,50,1500,50));
        System.out.println("Draw " +afterRatingForPlayer(MatchResult.DRAW,1500,50,1500,50));
        System.out.println("Lose " +afterRatingForPlayer(MatchResult.LOSE,1500,50,1500,50));

        System.out.println("================ = ");


        // 평균 평점보다 10점 높음 ->1
        System.out.println("win " +afterRatingForPlayer(MatchResult.WIN,1500,60,1500,50));
        System.out.println("Draw " +afterRatingForPlayer(MatchResult.DRAW,1500,60,1500,50));
        System.out.println("Lose " +afterRatingForPlayer(MatchResult.LOSE,1500,60,1500,50));

        System.out.println("================ = ");


        // 평균 평점보다 20점 높음 ->2
        System.out.println("win " +afterRatingForPlayer(MatchResult.WIN,1500,70,1500,50));
        System.out.println("Draw " +afterRatingForPlayer(MatchResult.DRAW,1500,70,1500,50));
        System.out.println("Lose " +afterRatingForPlayer(MatchResult.LOSE,1500,70,1500,50));   System.out.println("================ = ");


        // 평균 평점보다 40점 높음 - 4
        System.out.println("win " +afterRatingForPlayer(MatchResult.WIN,1500,90,1500,50));
        System.out.println("Draw " +afterRatingForPlayer(MatchResult.DRAW,1500,90,1500,50));
        System.out.println("Lose " +afterRatingForPlayer(MatchResult.LOSE,1500,90,1500,50));






    }


    private double afterRatingForPlayer(MatchResult matchResult , double myRating ,int myGrade , int oppositeAvgRating , int totalAvgGrade){
        double w = 0;
        double a = 10;
        double b = (oppositeAvgRating - myRating) / VI;
        double we = 1 / (Math.pow(a,b) + 1);
        if(matchResult.equals(MatchResult.WIN)){
            w = 1;
        }
        else if(matchResult.equals(MatchResult.DRAW)){
            w = 0.5;
        }
        double r = myRating + K * (w - we);
        r += 5*(((double)myGrade / totalAvgGrade) - 1);
        r = Math.round(r * 100) / 100.0;
        return r;
    }

}
