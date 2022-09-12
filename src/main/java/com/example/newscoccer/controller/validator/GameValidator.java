package com.example.newscoccer.controller.validator;

import com.example.newscoccer.RegisterService.round.common.game.GameRecordDto;
import com.example.newscoccer.SearchService.round.common.game.GameResultPlayerDto;
import com.example.newscoccer.SearchService.round.common.game.GameResultResponse;
import com.example.newscoccer.SearchService.round.common.game.GameResultTeamDto;

import java.util.List;

public class GameValidator {
    public  static boolean gameRecordHasError(GameRecordDto dto){
        boolean hasError = false;
        GameResultTeamDto teamDtoA = dto.getTeams().get(0);
        GameResultTeamDto teamDtoB = dto.getTeams().get(1);

        List<GameResultPlayerDto> playerAs = dto.getPlayers().subList(0,11);
        List<GameResultPlayerDto> playerBs = dto.getPlayers().subList(11,22);


        if(teamDtoA.getScore() != playerAs.stream().mapToInt(ele->ele.getGoal()).sum()) hasError = true;
        if(teamDtoB.getScore() != playerBs.stream().mapToInt(ele->ele.getGoal()).sum()) hasError = true;


        if(teamDtoA.getShare() + teamDtoB.getShare() < 97) hasError = true;


        for( var ele : dto.getPlayers()){
            if(ele.getValidShooting() > 50) hasError = true;
            if(ele.getGrade() >100) hasError = true;
            if(ele.getGoal() > 10) hasError = true;
            if(ele.getAssist() > 20) hasError = true;
            if(ele.getPass()>150) hasError = true;
            if(ele.getShooting()  > 50) hasError = true;
            if(ele.getValidShooting() > 50) hasError = true;
            if(ele.getDefense() > 100) hasError = true;
        }
        return hasError;
    }

    public static boolean gamePairHasError(GameResultResponse dto,List<Long> goalPlayerList,List<Long>assistPLayerList){
        List<GameResultPlayerDto> players = dto.getPlayerADtoList();
        dto.getPlayerBDtoList().stream().forEach(ele->players.add(ele));

        boolean hasError = false;
        for(var ele : players){
            int goal = ele.getGoal();
            int curValue = 0;
            for(var cur : goalPlayerList){
                if(cur.equals(ele.getPlayerId())) curValue += 1;
            }
            if(goal != curValue) hasError = true;
        }

        for(var ele : players){
            int assist = ele.getAssist();
            int curValue = 0;
            for(var cur : assistPLayerList){
                if(cur.equals(ele.getPlayerId())) curValue += 1;
            }
            if(assist != curValue) hasError = true;
        }
        return hasError;
    }








}
