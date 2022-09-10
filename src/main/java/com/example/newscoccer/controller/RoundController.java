package com.example.newscoccer.controller;

import com.example.newscoccer.RegisterService.round.common.GoalAssistPair.GoalAssistPair;
import com.example.newscoccer.RegisterService.round.common.GoalAssistPair.GoalAssistPairDto;
import com.example.newscoccer.RegisterService.round.common.game.GameRecordDto;
import com.example.newscoccer.RegisterService.round.common.game.GameRecordRegister;
import com.example.newscoccer.RegisterService.round.common.lineUp.LineUpRegister;
import com.example.newscoccer.RegisterService.round.common.lineUp.LineUpResult;
import com.example.newscoccer.RegisterService.round.common.lineUp.LineUpResultDto;
import com.example.newscoccer.SearchService.round.championsRound.ChampionsRoundInfo;
import com.example.newscoccer.SearchService.round.championsRound.ChampionsRoundInfoRequest;
import com.example.newscoccer.SearchService.round.common.faceToHead.*;
import com.example.newscoccer.SearchService.round.common.game.*;
import com.example.newscoccer.SearchService.round.common.goalAssistPair.GoalAssistPairInfo;
import com.example.newscoccer.SearchService.round.common.goalAssistPair.GoalAssistPairSearch;
import com.example.newscoccer.SearchService.round.common.lineUp.RoundLineUp;
import com.example.newscoccer.SearchService.round.common.lineUp.RoundLineUpRequest;
import com.example.newscoccer.SearchService.round.common.lineUp.RoundLineUpResponse;
import com.example.newscoccer.SearchService.round.leagueRound.LeagueRoundInfo;
import com.example.newscoccer.SearchService.round.leagueRound.LeagueRoundInfoRequest;
import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundStatus;
import com.example.newscoccer.domain.SeasonUtils;
import com.example.newscoccer.domain.record.GoalType;
import com.example.newscoccer.springDataJpa.LeagueRepository;
import com.example.newscoccer.springDataJpa.RoundRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  Get round/league -> 리그 라운드 정보 , 4개 리그를 모두 보여줌 .
 *  Get round/champions ->챔피언스 라운드 정보
 *
 *  Get ,POST round/line-up/{roundId} 라운드 공통 처리
 *
 *  GET /round/power/{roundId} -> 상대 전력 페이지
 *
 *  GET ,POST /round/game{roundId} -> 게임 페이지
 *
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/round")
public class RoundController {

    private final LeagueRoundInfo leagueRoundInfo;
    private final LeagueRepository leagueRepository;
    private final ChampionsRoundInfo championsRoundInfo;
    private final RoundLineUp roundLineUp;
    private final LineUpRegister lineUpRegister;
    private final FaceToHead faceToHead;
    private final GameRecordRegister gameRecordRegister;
    private final GameResult gameResult;
    private final RoundRepository roundRepository;
    private final GoalAssistPair goalAssistPair;
    private final GoalAssistPairSearch goalAssistPairSearch;
    private final static String lineUpErrorMessage = "경기에 참가할 수 있는 선수는 11명이며 골기퍼는 반드시 한명이 있어야 합니다.";

    /**
     * season , roundSt 가 정상 입력이 아닌 경우 : 빈 정보  vs  NotFound
     */
    @GetMapping("/league")
    public String leagueRound(@RequestParam(required = false) Integer season,
                              @RequestParam(required = false) Integer roundSt,
                              Model model){
        if(season == null) season = SeasonUtils.currentSeason;
        if(roundSt == null) roundSt = SeasonUtils.currentLeagueRoundSt;
        if(season < 0 || season > SeasonUtils.currentSeason) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if(roundSt < 0 || roundSt > SeasonUtils.lastLeagueRoundSt) throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        model.addAttribute("season",season);
        model.addAttribute("roundSt",roundSt);
        model.addAttribute("Seasons",SeasonUtils.currentSeason);
        model.addAttribute("lastRound", SeasonUtils.lastLeagueRoundSt);
        model.addAttribute("currentRound", SeasonUtils.currentLeagueRoundSt);


        List<League> leagueList = leagueRepository.findAll();

        model.addAttribute("GLeague",leagueRoundInfo.leagueRoundInformation(
                new LeagueRoundInfoRequest(leagueList.get(0).getId(),season,roundSt)).getRoundInfoDtoList());

        model.addAttribute("LLeague",leagueRoundInfo.leagueRoundInformation(
                new LeagueRoundInfoRequest(leagueList.get(1).getId(),season,roundSt)).getRoundInfoDtoList());
        model.addAttribute("ELeague",leagueRoundInfo.leagueRoundInformation(
                new LeagueRoundInfoRequest(leagueList.get(2).getId(),season,roundSt)).getRoundInfoDtoList());
        model.addAttribute("SLeague",leagueRoundInfo.leagueRoundInformation(
                new LeagueRoundInfoRequest(leagueList.get(3).getId(),season,roundSt)).getRoundInfoDtoList());
        return "round/league";
    }


    /**
     * * TODO : 챔피언스 결승전일 경우 뷰를 다르게 내려주어야함.
     */
    @GetMapping("/champions")
    public String championsRound(@RequestParam(required = false)Integer season,
                                 @RequestParam(required = false)Integer roundSt,
                                 Model model){

        if(season == null) season = SeasonUtils.currentSeason;
        if(roundSt == null) roundSt = SeasonUtils.currentChampionsRoundSt;
        if(season < 0 || season > SeasonUtils.currentSeason) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if(roundSt % 2 != 0 ||  roundSt > 16) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        model.addAttribute("roundSt",roundSt);
        model.addAttribute("season",season);
        model.addAttribute("Seasons",SeasonUtils.currentSeason);

        model.addAttribute("ChampionsRoundInfoFirst",championsRoundInfo.championsRoundInformation(new ChampionsRoundInfoRequest(season, roundSt)).getFirstRound());
        model.addAttribute("ChampionsRoundInfoSecond",championsRoundInfo.championsRoundInformation(new ChampionsRoundInfoRequest(season, roundSt)).getSecondRound());


        return "round/champions";
    }


    /**
     * 라인업 페이지
     */
    @GetMapping(value = {"/line-up/{roundId}"})
    public String lineUpPage(@PathVariable Long roundId ,
                             @RequestParam(required = false,value ="error-message")Boolean errorMessage ,Model model){

        model.addAttribute("errorMessage",errorMessage == null ? null :lineUpErrorMessage);
        model.addAttribute("roundId",roundId);
        model.addAttribute("positionList", Position.values());
        RoundLineUpResponse resp = roundLineUp.lineUp(new RoundLineUpRequest(roundId));
        model.addAttribute("roundLineUpResponse",resp);
        model.addAttribute("newData",new NewData(resp));
        return "round/lineUp";
    }

    /*
     *
     *
     *
     * 라인업 저장
     *
     * A팀 11명 , B팀 11명 , 각각 골기퍼는 한명씩만 .
     *
     *
     * - 주요기능 :newData 와 original 를 비교해서  lineUpRegister 에 전달.
     * - validation : A 팀 : 11 , B 팀 : 11 이러한 과정 중 A팀 인덱스 보다 적은 수가 11명 선택 ,  A 팀 인덱스보다 큰 수가 11명 선택 -> value = 11110
     *              : 골기퍼 한명씩 , 11명 이하일때 , 이상일 때 각각 한명씩 있는지 확인 후 10010 나와야함.
     */
    @PostMapping(value = {"/line-up/{roundId}"})
    public String lineUp(@PathVariable Long roundId, @ModelAttribute NewData newData , RedirectAttributes redirectAttributes){
        RoundLineUpResponse resp = roundLineUp.lineUp(new RoundLineUpRequest(roundId));
        NewData original = new NewData(resp);
        LineUpResultDto lineUpResultDto = new LineUpResultDto();
        lineUpResultDto.setRoundId(roundId);
        int hasError = 0;
        for (var p : newData.getJoinPlayer()) {
            int index = original.getJoinPlayer().indexOf(p);
            Position position = newData.getJoinPosition().get(index);
            if(index < resp.getPlayerListA().size()) hasError += 10;
            else hasError += 1000;
            lineUpResultDto.addParticipatePlayer(new LineUpResult(p,position));
        }

        if(lineUpResultDto.getValue() != 10010) hasError += 1;
        if(hasError != 11110){
            redirectAttributes.addAttribute("error-message",true);
            return "redirect:/round/line-up/" + roundId;
        }

        lineUpRegister.lineUpRegister(lineUpResultDto);
         return "redirect:/round/line-up/" + roundId;
    }


    /**
     *
     * 상대 전력 페이지
     * TODO : 데이터가 많이 들어간 후에 유효성 검증
     */
    @GetMapping("/power/{roundId}")
    public String powerPage(@PathVariable Long roundId, Model model){

        ComparisonRecordResponse comparisonResp = faceToHead.comparison(roundId);
        model.addAttribute("comparisonA",comparisonResp.getRecordList().get(0));
        model.addAttribute("comparisonB",comparisonResp.getRecordList().get(1));

        RecentRecordResponse recentRecordResponse = faceToHead.recentRecord(roundId);
        model.addAttribute("recentShowDown",recentRecordResponse.getSimpleRecordResultDtoList());

        TopPlayerResponse topPlayerResponse = faceToHead.top5Player(roundId);
        model.addAttribute("teamATopPlayer",topPlayerResponse.getTeamAPlayerList());
        model.addAttribute("teamBTopPlayer",topPlayerResponse.getTeamBPlayerList());

        TotalComparisonRecordResponse totalComparisonRecordResponse = faceToHead.totalComparison(roundId);
        model.addAttribute("totalShowDown",totalComparisonRecordResponse);

        return "round/power";
    }

    /**
     * 게임 페이지
     */
    @GetMapping("/game/{roundId}")
    public String gamePage(@PathVariable Long roundId , Model model){
        Round round = roundRepository.findById(roundId).orElse(null);

        if(round.getRoundStatus() == RoundStatus.YET) return "error/gameStartBeforeError";
        if(round.getRoundStatus() == RoundStatus.PAIR) return "redirect:/round/game-pair/" + roundId;
        if(round.getRoundStatus() == RoundStatus.DONE) return "redirect:/round/game-done/" + roundId;

        GameResultResponse gameResultResponse = gameResult.gameResult(new GameResultRequest(roundId));

        model.addAttribute("gameResultResponse",gameResultResponse);
        model.addAttribute("gameData" , new GameData());
        return "round/game";
    }

    @PostMapping("/game/{roundId}")
    public String game(@PathVariable Long roundId , @ModelAttribute GameData gameData){
        GameResultResponse resp = gameResult.gameResult(new GameResultRequest(roundId));
        GameRecordDto dto = gameData.dataTransfer(roundId, resp);
        gameRecordRegister.gameRecordRegister(dto);
        return "redirect:/round/game/" +  roundId;
    }



    @GetMapping("/game-pair/{roundId}")
    public String gamePairPage(@PathVariable Long roundId , Model model){

        GameResultResponse resp = gameResult.gameResult(new GameResultRequest(roundId));
        model.addAttribute("gameResult",resp);
        model.addAttribute("goalTypeList", GoalType.values());

        int count = 0;
        count += resp.getPlayerADtoList().stream().mapToInt(ele->ele.getGoal()).sum();
        count += resp.getPlayerBDtoList().stream().mapToInt(ele->ele.getGoal()).sum();
        List<PlayerSimInfo> playerList = new ArrayList<>();
        playerList.add(new PlayerSimInfo(0L,"없음"));
        playerList.addAll(
                resp.getPlayerADtoList().stream()
                        .map(ele->new PlayerSimInfo(ele.getPlayerId(),ele.getPlayerName())).collect(Collectors.toList()));
        playerList.addAll(
                resp.getPlayerBDtoList().stream()
                        .map(ele->new PlayerSimInfo(ele.getPlayerId(),ele.getPlayerName())).collect(Collectors.toList()));

        model.addAttribute("pairRecord",new PairRecord());
        model.addAttribute("count",count);
        model.addAttribute("playerList",playerList);
        model.addAttribute("goalTypeList", GoalType.values());

        return "round/gamePair";
    }

    @PostMapping("/game-pair/{roundId}")
    public String gamePair(@PathVariable Long roundId , @ModelAttribute PairRecord pairRecord){
        goalAssistPair.goalPairRegister(pairRecord.dataTransfer(),roundId);
        return "redirect:/round/game/" + roundId;
    }



    @GetMapping("/game-done/{roundId}")
    public String gameDonePage(@PathVariable Long roundId , Model model) {
        List<GoalAssistPairInfo> goalAssistPairInfos = goalAssistPairSearch.goalAssistPairSearch(roundId);
        model.addAttribute("duoResultResponse",goalAssistPairInfos);
        GameResultResponse resp = gameResult.gameResult(new GameResultRequest(roundId));
        model.addAttribute("gameResult",resp);
        return "round/gameDone";
    }





    @Data
    static class PairRecord{
        List<Long> goalPlayer = new ArrayList<>();
        List<Long> assistPlayer = new ArrayList<>();
        List<GoalType> goalType = new ArrayList<>();


        public List<GoalAssistPairDto> dataTransfer() {
            List<GoalAssistPairDto> ret = new ArrayList<>();
            for(int i = 0 ;i <goalType.size();i++){
                GoalType type = goalType.get(i);
                ret.add(new GoalAssistPairDto(goalPlayer.get(i),assistPlayer.get(i),type));
            }
            return ret;
        }
    }
    @AllArgsConstructor
    @Getter
    static class PlayerSimInfo{
        private Long playerId;
        private String playerName;
    }






    /**
     *  폼 데이터와 비지니스 계층 사이에 Transfer data
     */
    @Data
    @NoArgsConstructor
    static class NewData{
        private List<Position> joinPosition = new ArrayList<>();
        private List<Long> joinPlayer = new ArrayList<>();

        public NewData(RoundLineUpResponse resp) {
            resp.getPlayerListA().stream().forEach(p->{
                joinPlayer.add(p.getPlayerId());
            });
            resp.getPlayerListB().stream().forEach(p->{
                joinPlayer.add(p.getPlayerId());
            });

        }
    }



    @Data
    @NoArgsConstructor
    static class GameData{

        List<Integer> scorePair = new ArrayList<>();
        List<Integer> sharePair = new ArrayList<>();
        List<Integer> cornerKickPair = new ArrayList<>();
        List<Integer> freeKickPair = new ArrayList<>();



        List<Integer> goalList = new ArrayList<>();
        List<Integer> assistList = new ArrayList<>();
        List<Integer> passList = new ArrayList<>();
        List<Integer> shootingList = new ArrayList<>();
        List<Integer> validShootingList = new ArrayList<>();
        List<Integer> foulList = new ArrayList<>();
        List<Integer> goodDefenseList = new ArrayList<>();
        List<Integer> gradeList = new ArrayList<>();

        public GameRecordDto dataTransfer(Long roundId,GameResultResponse resp){
            int pos = 0;
            GameRecordDto dto = new GameRecordDto();
            dto.setRoundId(roundId);
            teamProcess(dto,0,resp.getTeamA());
            teamProcess(dto,1,resp.getTeamB());

            pos = playerProcess(dto,pos,resp.getPlayerADtoList());
            playerProcess(dto,pos,resp.getPlayerBDtoList());
            return dto;
        }

        private void teamProcess(GameRecordDto dto , int index, GameResultTeamDto teamDto){
            GameResultTeamDto teamDtoA = new GameResultTeamDto(teamDto.getTeamId(), teamDto.getTeamName());
            teamDtoA.setScore(scorePair.get(index));
            teamDtoA.setShare(Math.round(sharePair.get(index) * 100 ) / 100.0); // Math.round(a * 100) / 100.0
            teamDtoA.setCornerKick(cornerKickPair.get(index));
            teamDtoA.setFreeKick(freeKickPair.get(index));
            dto.getTeams().add(teamDtoA);
        }
        private int playerProcess(GameRecordDto dto , int pos , List<GameResultPlayerDto> list){
            for (var p : list) {
                GameResultPlayerDto playerDto = new GameResultPlayerDto(p.getPlayerId(),p.getPlayerName(),p.getPosition());
                int goal = goalList.get(pos);
                int assist = assistList.get(pos);
                int pass = passList.get(pos);
                int shooting = shootingList.get(pos);
                int validShooting = validShootingList.get(pos);
                int foul = foulList.get(pos);
                int defense = goodDefenseList.get(pos);
                int grade = gradeList.get(pos);
                playerDto.dataSetting(goal,assist,pass,shooting,validShooting,foul,defense,grade);
                dto.getPlayers().add(playerDto);
                pos+=1;
            }
            return pos;
        }
    }
}
