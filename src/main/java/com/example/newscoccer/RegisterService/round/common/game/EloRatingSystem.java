package com.example.newscoccer.RegisterService.round.common.game;

import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundFeature;
import com.example.newscoccer.domain.Round.RoundTemplate;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.*;
import com.example.newscoccer.springDataJpa.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 *  레이팅 시스템.
 *
 *
 *
 */
@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class EloRatingSystem {

    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final TeamChampionsRecordRepository teamChampionsRecordRepository;

    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final PlayerChampionsRecordRepository playerChampionsRecordRepository;

    private static int VI = 400;
    private static Integer K = 20;

    private final LeagueRepository leagueRepository;

    public void ratingSetting(Round round){

        RoundFeature<Void> roundFeature = new RoundFeature<>() {
            @Override
            public Void leagueSolved() {

                List<TeamLeagueRecord> teamRecords = teamLeagueRecordRepository.findByRound(round);

                //팀 레이팅 계산.
                calcTeamRating(teamRecords.get(0),teamRecords.get(1).getRating());
                calcTeamRating(teamRecords.get(1),teamRecords.get(0).getRating());



                List<PlayerRecord> plrListA = playerLeagueRecordRepository.findByTeamAndRound(teamRecords.get(0).getTeam(),round).stream().collect(toList());
                List<PlayerRecord> plrListB = playerLeagueRecordRepository.findByTeamAndRound(teamRecords.get(1).getTeam(),round).stream().collect(toList());

                // 선수 레이팅 계산.
                calcPlayerRating(plrListA,new RatingInfo(plrListA,plrListB));
                calcPlayerRating(plrListB,new RatingInfo(plrListB,plrListA));


                return null;

            }

            @Override
            public Void championsSolved() {
                List<TeamChampionsRecord> teamRecords = teamChampionsRecordRepository.findByRound(round);
                //팀 레이팅 계산.
                calcTeamRating(teamRecords.get(0),teamRecords.get(1).getRating());
                calcTeamRating(teamRecords.get(1),teamRecords.get(0).getRating());


                List<PlayerRecord> pcrListA = playerChampionsRecordRepository.findByTeamAndRound(teamRecords.get(0).getTeam(),round).stream().collect(toList());
                List<PlayerRecord> pcrListB = playerChampionsRecordRepository.findByTeamAndRound(teamRecords.get(1).getTeam(),round).stream().collect(toList());
                // 선수 레이팅 계산.
                calcPlayerRating(pcrListA.stream().collect(toList()),new RatingInfo(pcrListA,pcrListB));
                calcPlayerRating(pcrListB.stream().collect(toList()),new RatingInfo(pcrListB,pcrListA));

                return null;
            }
        };

        new RoundTemplate().action(round,roundFeature);

    }

    /**
     *
     * 리그 - 1위 +50
     *     - 2위 +25
     *     - 3위 +12
     *     - 4위 + 6
     *
     *     - 5 , 6 , 7 , 8 ( + 2  + 2 + 1 +1)
     *     - 9 , 10,11, 12 ( 0 0 0 0 )
     *     - 13, 14,15  16 (-2 -2 -4 -5)
     *
     *
     * 챔피언스 리그 - 우승 ->70
     *            - 준우승 ->40
     *            - 4강 -> 20
     *            - 8강 -> 10
     *
     */
    public void seasonCompensation(int season){

        // 리그
        int leagueCompensation[] = new int[]{50,25,12,6,2,2,1,1,0,0,0,0,-2,-2,-4,-5};
        List<League> leagueList = leagueRepository.findAll();
        for (League league : leagueList) {
            List<TeamLeagueRecord> teamLeagueRecord = teamLeagueRecordRepository.findBySeasonTopN(league, season, PageRequest.of(0, 16));
            if(teamLeagueRecord.size() != 16)continue;
            for(int i = 0;i<16;i++){

                Team team = teamLeagueRecord.get(i).getTeam();
                Round round = teamLeagueRecord.get(i).getRound();
                int value = leagueCompensation[i];
                team.setRating(team.getRating() + value);

                playerLeagueRecordRepository.findByTeamAndRound(team,round)
                        .stream().forEach(p->{
                            Player player = p.getPlayer();
                            player.setRating(player.getRating() + value);
                        });
            }
        }
        //챔피언스
        Map<Team,Integer> teamRankMap = new HashMap<>();

        teamChampionsRecordRepository.findAllBySeason(season).stream()
                .forEach(tcr->{
                    Team team = tcr.getTeam();
                    if(!teamRankMap.containsKey(team)){
                       teamRankMap.put(team,tcr.getRank());
                    }
                });
        int championsCompensation[] = new int[]{0,70,40,0,20,0,0,0,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        for(var ele : teamRankMap.keySet()){

            int value = teamRankMap.get(ele);

                ele.setRating(ele.getRating() + championsCompensation[value]);
                playerChampionsRecordRepository.findPlayerParticipate(ele.getId(),season)
                        .stream().forEach(pr->{
                            Player player = pr.getPlayer();
                            player.setRating(player.getRating() + championsCompensation[value]);
                        });
        }
    }




    private void calcPlayerRating(List<PlayerRecord> playerRecord , RatingInfo ratingInfo){

        playerRecord.stream().forEach(pr->{
            double myRating = pr.getRating();
            int myGrade = pr.getGrade();
            double w = 0;
            double a = 10;
            double b = (ratingInfo.getAvgRating() - myRating) / VI;
            double we = 1 / (Math.pow(a,b) + 1);
            MatchResult matchResult = pr.getMatchResult();

            if(matchResult.equals(MatchResult.WIN)){
                w = 1;
            }
            else if(matchResult.equals(MatchResult.DRAW)){
                w = 0.5;
            }
            double r = myRating + K * (w - we);
            r += 5*(((double)myGrade / ratingInfo.getAvgGrade()) - 1);
            r = Math.round(r * 100) / 100.0;
            pr.setRating(r);
        });

    }
    private void calcTeamRating(TeamRecord teamRecord , double rating){
        Team team = teamRecord.getTeam();
        MatchResult matchResult = teamRecord.getMatchResult();
        double w = 0;
        double a = 10;
        double b = (rating - team.getRating()) / VI;
        double we = 1 / (Math.pow(a,b) + 1);
        if(matchResult  == MatchResult.WIN) w = 1;
        else if(matchResult  == MatchResult.DRAW) w = 0.5;
        double r = team.getRating() + K * (w - we);
        teamRecord.setRating(r);
    }


    @Getter
    static class RatingInfo{
        private double avgRating;
        private double avgGrade;

        public RatingInfo(List<PlayerRecord> listA,List<PlayerRecord> listB) {
            setAvgRating(0,listB);
            setAvgGrade(listA,listB);
        }

        private void setAvgRating(int index, List<PlayerRecord> list){
            this.avgRating = list.stream().mapToDouble(ele->ele.getRating()).average().getAsDouble();

        }
        private void setAvgGrade(List<PlayerRecord> listA,List<PlayerRecord> listB){
            double avgGradeA = listA.stream().mapToDouble(ele->ele.getGrade()).average().getAsDouble();
            double avgGradeB = listB.stream().mapToDouble(ele->ele.getGrade()).average().getAsDouble();
            this.avgGrade = (avgGradeA + avgGradeB)/2;
        }

    }

}
