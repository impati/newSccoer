package com.example.newscoccer.SearchService.team.info.league;

import com.example.newscoccer.SearchService.common.EntityRecordInfo;
import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.Round.LeagueRound;
import com.example.newscoccer.domain.Round.RoundStatus;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.record.MatchResult;
import com.example.newscoccer.domain.record.PlayerLeagueRecord;
import com.example.newscoccer.domain.record.TeamLeagueRecord;
import com.example.newscoccer.springDataJpa.*;
import com.example.newscoccer.support.RandomNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DefaultTeamLeagueInfoTest {

    @Autowired
    EntityRecordInfo<TeamLeagueInfoRequest, TeamLeagueInfoResponse> recordInfo;

    @Autowired
    PlayerLeagueRecordRepository playerLeagueRecordRepository;

    @Autowired
    TeamLeagueRecordRepository teamLeagueRecordRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    RoundRepository roundRepository;


    @Test
    @DisplayName("팀 리그 정보 페이징")
    public void TeamLeagueInfo() throws Exception{

        // given


        League league = new League("testLeague");
        leagueRepository.save(league);

        Team team = Team.createTeam(league,"testTeam");
        teamRepository.save(team);

        for(int i = 0;i<11;i++){
            Player player = Player.createPlayer("test" + i, Position.ST,team,new Stat());
            playerRepository.save(player);

        }


        int rank = -1, gain = 0, lost = 0,point = 0;
        for(int i = 1;i<=15;i++){
            LeagueRound leagueRound = new LeagueRound(league,0,i);
            leagueRound.setRoundStatus(RoundStatus.DONE);
            roundRepository.save(leagueRound);

            TeamLeagueRecord tlr = TeamLeagueRecord.create(leagueRound,team);
            //데이터 조작.


            playerRepository.findByTeam(team).stream().forEach(ele->{
                PlayerLeagueRecord plr = (PlayerLeagueRecord) PlayerLeagueRecord.createPlayerRecord(ele,ele.getPosition(),team,leagueRound);
                playerLeagueRecordRepository.save(plr);
            });

            int r =  RandomNumber.returnRandomNumber(1,16);
            tlr.setRank(r);

            int g = RandomNumber.returnRandomNumber(1,5);
            int l = RandomNumber.returnRandomNumber(1,5);

            gain += g;
            lost += l;
            if(g > l) {
                tlr.setMatchResult(MatchResult.WIN);
                point += 3;
            }
            else if(g == l) {
                tlr.setMatchResult(MatchResult.DRAW);
                point += 1;
            }
            else tlr.setMatchResult(MatchResult.LOSE);

            tlr.setRank(r);
            tlr.setScore(g);
            tlr.setOppositeScore(l);
            if(i == 15) rank = r;

            teamLeagueRecordRepository.save(tlr);

        }

        // when
        TeamLeagueInfoResponse resp = recordInfo.recordInfo(new TeamLeagueInfoRequest(team.getId(),0));

        // then
        assertThat(resp.getGameNumber()).isEqualTo(15);
        assertThat(resp.getRank()).isEqualTo(rank);
        assertThat(resp.getGain()).isEqualTo(gain);
        assertThat(resp.getLost()).isEqualTo(lost);
        assertThat(resp.getPoint()).isEqualTo(point);
        assertThat(resp.getParticipatePlayers().size()).isEqualTo(11);
        for(int i = 0 ;i<resp.getParticipatePlayers().size();i++){
            assertThat(resp.getParticipatePlayers().get(i).getGame()).isEqualTo(15);
        }


    }
}