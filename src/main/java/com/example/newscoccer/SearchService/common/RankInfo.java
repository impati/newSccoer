package com.example.newscoccer.SearchService.common;

import com.example.newscoccer.domain.DataTransferObject;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundStatus;
import com.example.newscoccer.domain.record.*;
import com.example.newscoccer.domain.SeasonUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
public class RankInfo extends DataTransferObject {
    private int leagueFirst;
    private int leagueSecond;
    private int leagueThird;
    private int leagueFour;

    private int championsFirst;
    private int championsSecond;
    private int championsThird;
    private int championsFour;


    /**
     *  리그 인지 챔피언스 리그인지 처리후 메서드 호출해줌 .
     * @param records
     * @param clazz
     */
    public void calcRank(List records, Class clazz){

        if(clazz.isAssignableFrom(PlayerLeagueRecord.class) || clazz.isAssignableFrom(TeamLeagueRecord.class)){
            leagueTakeCareOf(records,clazz);
        }
        else if(clazz.isAssignableFrom(PlayerChampionsRecord.class) || clazz.isAssignableFrom(TeamChampionsRecord.class)){
            championsTakeCareOf(records,clazz);
        }
        else{
            //
        }

    }

    /**
     * records : 시즌 마지막 경기들
     * @param records
     * @param clazz
     */
    private void leagueTakeCareOf(List records,Class clazz){
        for(var record : records) {
            if (clazz.isAssignableFrom(PlayerLeagueRecord.class)) {
                PlayerLeagueRecord playerLeagueRecord = (PlayerLeagueRecord)record;
                Round round = playerLeagueRecord.getRound();
                if(round.getRoundSt() == SeasonUtils.currentLeagueRoundSt && round.getRoundStatus() == RoundStatus.DONE){
                    leagueCalc(playerLeagueRecord.getRank());
                }
            }
            else {

                TeamLeagueRecord teamLeagueRecord = (TeamLeagueRecord)record;
                Round round = teamLeagueRecord.getRound();
                if(round.getRoundSt() == SeasonUtils.currentLeagueRoundSt && round.getRoundStatus() == RoundStatus.DONE){
                    leagueCalc(teamLeagueRecord.getRank());
                }
            }
        }

    }

    /**
     *
     * records : 시즌 경기 모두 .
     * @param records
     * @param clazz
     */
    private void championsTakeCareOf(List records,Class clazz){
        int minValue = 987654321;
           for(var record : records){
               if(clazz.isAssignableFrom(PlayerChampionsRecord.class)){
                   PlayerChampionsRecord playerChampionsRecord = (PlayerChampionsRecord) record;
                   minValue = Math.min(playerChampionsRecord.getRank(),minValue);
               }
               else{
                   TeamChampionsRecord teamChampionsRecord = (TeamChampionsRecord) record;
                   minValue = Math.min(teamChampionsRecord.getRank(),minValue);
               }
           }
        championsCalc(minValue);
    }



    private void championsCalc(int r){
        switch (r){
            case 1:
                championsFirst += 1;break;
            case 2:
                championsSecond += 1;break;
            case 3:
                championsThird +=1;break;
            case 4:
                championsFour +=1; break;
            default:
                break;
        }
    }

    private void leagueCalc(int r){
        switch (r){
            case 1:
                leagueFirst += 1;break;
            case 2:
                leagueSecond += 1;break;
            case 3:
                leagueThird +=1;break;
            case 4:
                leagueFour +=1; break;
            default:
                break;
        }
    }







}
