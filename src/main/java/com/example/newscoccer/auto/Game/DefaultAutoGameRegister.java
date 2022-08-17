package com.example.newscoccer.auto.Game;


import com.example.newscoccer.RegisterService.round.common.GoalAssistPair.GoalAssistPair;
import com.example.newscoccer.RegisterService.round.common.GoalAssistPair.GoalAssistPairDto;
import com.example.newscoccer.RegisterService.round.common.game.GameRecordDto;
import com.example.newscoccer.RegisterService.round.common.game.GameRecordRegister;
import com.example.newscoccer.SearchService.round.common.game.GameResultPlayerDto;
import com.example.newscoccer.SearchService.round.common.game.GameResultTeamDto;
import com.example.newscoccer.SearchService.round.common.lineUp.RoundLineUp;
import com.example.newscoccer.SearchService.round.common.lineUp.RoundLineUpDto;
import com.example.newscoccer.SearchService.round.common.lineUp.RoundLineUpRequest;
import com.example.newscoccer.SearchService.round.common.lineUp.RoundLineUpResponse;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.springDataJpa.PlayerRepository;
import com.example.newscoccer.springDataJpa.RoundRepository;
import com.example.newscoccer.support.RandomNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *  1.선수들의 패스 수를 구한다. -> 평균도 구한다
 *  2.선수들의 차단 수를 구한다. -> 평균도 구한다.
 *
 *  3. (패스 - 상대 차단) 비율로 -> 점유율을 구한다.
 *
 *  4. 파울 수를 구한후 -> 확률 -> 상대 프리킥 기회
 *
 *
 *  5. 공격 기회  - > 확률 -> 코너킥 기회
 *  6. 슈팅 -> 확률 -> 유효 슈팅 -> 확률 -> 골
 *  7. 어시스터는 스탯 기준으로 높은 확률을 받아 랜덤으로 선택이 됨
 *
 *
 *  1 .저장된 라인업을 가져와 스탯에 따른 경기결과를 계산해서 저장한다
 *  2 .게산된 골 - 어시 정보로 골 - 어시 쌍도 저장한다.
 *  3. 평점을 등록한다
 */
@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class DefaultAutoGameRegister implements AutoGameRegister{

    private final RoundLineUp roundLineUp;
    private final PlayerRepository playerRepository;
    private final AutoGradeDecision autoGradeDecision;
    private final GoalAssistPair goalAssistPair;
    private final GameRecordRegister gameRecordRegister;
    private final RoundRepository roundRepository;
    @Override
    public void autoGameRegister(Long roundId) {
        Round round = roundRepository.findById(roundId).orElse(null);
        RoundLineUpResponse resp = roundLineUp.lineUp(new RoundLineUpRequest(roundId));

        // 선수
        AutoGameDto autoGameDtoA = new AutoGameDto(resp.getTeamAId(),resp.getTeamAName());
        AutoGameDto autoGameDtoB = new AutoGameDto(resp.getTeamBId(),resp.getTeamBName());

        // 상대 객체를 참조
        autoGameDtoA.setOpposite(autoGameDtoB);
        autoGameDtoB.setOpposite(autoGameDtoA);


        // 패스 디펜스 파울 세팅
        passAndDefenseFoulSetting(resp.getPlayerListA(),autoGameDtoA);
        passAndDefenseFoulSetting(resp.getPlayerListB(),autoGameDtoB);

        // 점유율
        autoGameDtoA.updateShare();
        autoGameDtoB.updateShare();



        // 파울
        autoGameDtoA.updateFoul();
        autoGameDtoB.updateFoul();





        // 프리킼
        autoGameDtoA.updateFreeKick();
        autoGameDtoB.updateFreeKick();




        //슈팅 골 어시
        autoGameDtoA.updateGoalAndAssist();
        autoGameDtoB.updateGoalAndAssist();





        //코너킥
        autoGameDtoA.updateCornerKick();
        autoGameDtoB.updateCornerKick();


        // test complete


        // 스코어 세팅
        autoGameDtoA.updateScore();
        autoGameDtoB.updateScore();

        // 평점

        autoGameDtoA.getPlayerList().stream().forEach(p->{
            autoGradeDecision.gradeDecision(p);
        });
        autoGameDtoB.getPlayerList().stream().forEach(p->{
            autoGradeDecision.gradeDecision(p);
        });
        GameRecordDto ret = new GameRecordDto();
        ret.setRoundId(roundId);


        // 선수 정보 세팅
         autoGameDtoA.getPlayerList().stream().map(p->new GameResultPlayerDto(p.getPlayerId(),p.getPlayerName(),p.getParticipatePosition(),
                p.getGoal(),p.getAssist(),p.getPass(),
                p.getShooting(),p.getValidShooting(),p.getFoul(),
                p.getDefense(),p.getGrade()
        )).forEach(ele->ret.getPlayers().add(ele));


         autoGameDtoB.getPlayerList().stream().map(p->new GameResultPlayerDto(p.getPlayerId(),p.getPlayerName(),p.getParticipatePosition(),
                p.getGoal(),p.getAssist(),p.getPass(),
                p.getShooting(),p.getValidShooting(),p.getFoul(),
                p.getDefense(),p.getGrade()
        )).forEach(ele->ret.getPlayers().add(ele));


        // 팀 정보 세팅


        ret.getTeams().add(new GameResultTeamDto(autoGameDtoA.getTeamId(), autoGameDtoA.getTeamName(), autoGameDtoA.getScore(),
                autoGameDtoA.getShare(), autoGameDtoA.getCornerKick(), autoGameDtoA.getFreeKick()));

        ret.getTeams().add(new GameResultTeamDto(autoGameDtoB.getTeamId(), autoGameDtoB.getTeamName(), autoGameDtoB.getScore(),
                    autoGameDtoB.getShare(), autoGameDtoB.getCornerKick(), autoGameDtoB.getFreeKick()));

        gameRecordRegister.gameRecordRegister(ret);

        // pair 정보 넘기기

        List<GoalAssistPairDto> nxt = new ArrayList<>();
        autoGameDtoA.getGoalAssistPairs().stream().forEach(ele->nxt.add(ele));
        autoGameDtoB.getGoalAssistPairs().stream().forEach(ele->nxt.add(ele));


        goalAssistPair.goalPairRegister(nxt,round);
    }
    private void passAndDefenseFoulSetting(List<RoundLineUpDto> list , AutoGameDto autoGameDto){
        for (var lineUpInfo : list) {
            Player player = playerRepository.findById(lineUpInfo.getPlayerId()).orElse(null);
            AutoPersonalData personalData =  new AutoPersonalData(lineUpInfo.getPlayerId(),player.getName(),lineUpInfo.getPosition(),player.getStat(),returnCondition());
            // 패스
            personalData.setPass(PassDecision.passDecision(personalData.getParticipatePosition(),personalData.getCondition(),personalData.getStat()));
            // 디펜스
            personalData.setDefense(DefenseDecision.defenseDecision(personalData.getParticipatePosition(),personalData.getCondition(),personalData.getStat()));
            // 파울
            personalData.setFoul(FoulDecision.foulDecision(personalData.getParticipatePosition(),personalData.getCondition(),personalData.getStat()));

            autoGameDto.getPlayerList().add(personalData);
        }
    }


    private double returnCondition(){
        return RandomNumber.returnRandomNumber(0.7,1.4);
    }
}
