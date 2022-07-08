package com.example.newscoccer.domain.Round;

public class RoundTemplate{
    public <T> T action(Round round , LeagueFeature<T> leagueFeature , ChampionsFeature<T> championsFeature){
        if(round instanceof LeagueRound){
            return leagueFeature.solved();
        }
        else if(round instanceof  ChampionsRound){
            return championsFeature.solved();
        }
        return null;
    }
    public <T> T action(Round round , RoundFeature<T> roundFeature){
        if(round instanceof LeagueRound){
            return roundFeature.leagueSolved();
        }
        else if(round instanceof  ChampionsRound){
            return roundFeature.championsSolved();
        }
        return null;
    }
}
