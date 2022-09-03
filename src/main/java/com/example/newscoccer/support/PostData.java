package com.example.newscoccer.support;

import com.example.newscoccer.RegisterService.round.ChampionsRoundGenerator;
import com.example.newscoccer.RegisterService.round.LeagueRoundGenerator;
import com.example.newscoccer.auto.AutoStatConfig;
import com.example.newscoccer.auto.Game.AutoGameRegister;
import com.example.newscoccer.auto.lineUp.AutoLineUp;
import com.example.newscoccer.domain.League;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import com.example.newscoccer.domain.Season;
import com.example.newscoccer.domain.SeasonUtils;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.domain.director.Director;
import com.example.newscoccer.springDataJpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *  ******  개발 단계 초기화 클래스  ******
 *
 * 대전제 : 0. 있다면 세팅하지 않음.
 * 1. 시즌  세팅 . (method)
 * 2. 리그 정보 세팅 (method)
 * 3. 팀 정보 세팅. (method)
 * 4. 감독 정보 세팅. (method)
 * 5. 선수 정보 세팅. (method)
 *
 * 6. 시즌 리그 리운드 정보를 세팅. (interface)
 * 7. 챔피언스 라운드 정보를 세팅. (interface)
 *
 * ==> 기능으로 분리 vs 메서드로 남기기.
 * ==> PostData 클래스는 초기 설정을 하는 기능을 수행하며 각 메서드들은 다른 곳에서 사용되는 일이 드물것.(ex. 파일 정보를 읽어옴)
 * ==> 일단 메서드로 구현하고 재사용가능성이 빚어진다면 기능으로 구현.
 * ==> 개발 단계에서 필요한 작업들
 * ==> 시즌 라운드, 챔피언스 라운드는 재사용 가능성이 있으며 개발 단계의 클래스만이 이를 의존하지 않음.
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class PostData {
    private final AutoStatConfig autoStatConfig;
    private final SeasonRepository seasonRepository;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final DirectorRepository directorRepository;
    private final LeagueRoundGenerator leagueRoundGenerator;
    private final ChampionsRoundGenerator championsRoundGenerator;
    private final String filePath = "src/main/java/com/example/newscoccer/support/";
    private final RoundRepository roundRepository;
    private final AutoLineUp autoLineUp;
    private final AutoGameRegister autoGameRegister;
    private final int initialSize = 5;
    /**
     * 처음 한번만 .
     * 시즌이라는 기록도 없을 그 순간만.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void init() throws IOException {
        int exist = (int) seasonRepository.findAll().stream().count();
        if (exist == 0) {
            seasonSetting();
            leagueSetting();
            teamSetting();
            directorSetting();
            playerSetting();


            leagueSeasonSetting();
            championsSeasonSetting();




            log.info("===============end===================");
        }
        else{
            Season season = seasonRepository.findById(1L).get();
            SeasonUtils.currentLeagueRoundSt = season.getCurrentLeagueRoundSt();
            SeasonUtils.currentSeason = season.getCurrentSeason();
            SeasonUtils.currentChampionsRoundSt = season.getCurrentChampionsRoundSt();
            SeasonUtils.lastLeagueRoundSt = season.getLastLeagueRoundSt();
        }
    }
    private void seasonSetting(){
        Season season = new Season(); // 단 한번만 생성할 수 있음.
        seasonRepository.save(season);
        season.SeasonUtilsUpdate();
    }
    private void leagueSetting(){
        League b = new League("분데스리가");
        League l = new League("라리가");
        League e = new League("EPL");
        League s = new League("세리에");

        leagueRepository.save(b);
        leagueRepository.save(l);
        leagueRepository.save(e);
        leagueRepository.save(s);
    }
    private void teamSetting() throws IOException {
        String names[] = {"분데스리가","라리가","EPL","세리에"};
        for(Long i = 0L ; i < 4L ; i++) {
            BufferedReader cin = new BufferedReader(new FileReader(filePath + names[Math.toIntExact(i)] + ".txt"));
            List<String> list = new ArrayList<>();
            String tmp;
            while (true) {
                tmp = cin.readLine();
                if (tmp == null) break;
                list.add(tmp);
            }
            League league = leagueRepository.findById(i+1).orElse(null);
            for (var ele : list) {
                Team team = Team.createTeam(league, ele);
                teamRepository.save(team);
            }
            cin.close();
        }
    }

    private Position[] getPositionByFileName(String name){
        Position []position = null;
        if(name.equals("DefenderList")){
            position =  new Position[Position.DEFENDER_POSITION_COUNT];
            position[0] = Position.LB;
            position[1] = Position.RB;
            position[2] = Position.LWB;
            position[3] = Position.RWB;
            position[4] = Position.CB;
        }
        else if(name.equals("GoalKeeper")) {
            position = new Position[Position.GOALKEEPER_POSITION_COUNT];
            position[0] = Position.GK;
        }
        else if(name.equals("StrikerPlayerList")) {
            position = new Position[Position.STRIKER_POSITION_COUNT];
            position[0] = Position.ST;
            position[1] = Position.LF;
            position[2] = Position.RF;
            position[3] = Position.CF;
        }
        else {
            position = new Position[Position.MIDFIELDER_POSITION_COUNT];
            position[0] = Position.AM;
            position[1] = Position.LM;
            position[2] = Position.CM;
            position[3] = Position.RM;
            position[4] = Position.DM;
        }
        return position;
    }

    private void playerSetting() throws IOException{
        List<Team> teams = teamRepository.findAll();
        String names[] =  {"GoalKeeper","StrikerPlayerList","DefenderList","MidfielderPlayer"};
        for(String name : names) {
            String newPath = filePath + name + ".txt";
            BufferedReader cin = new BufferedReader(new FileReader(newPath));
            Position position[] = getPositionByFileName(name);
            int count = 0;
            while (true) {
                String temp = cin.readLine();
                if (temp == null) break;
                int rn = RandomNumber.returnRandomNumber(0, position.length - 1);
                Stat stat = Stat.createStat(0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 0, 0);
                Player player = Player.createPlayer(temp, position[rn], teams.get(count), stat);
                playerRepository.save(player);
                count += 1;
                count %= 64;
            }
        }

        teams.stream().forEach(t-> {
            List<Player> playerList = playerRepository.findByTeam(t);
            // 공격수 3명
            decisionMain(playerList,new Position[]{Position.ST, Position.CF , Position.RF ,Position.LF} , 3)
                    .stream()
                    .forEach(p->p.setMain(true));
            // 미드필더 3명
            decisionMain(playerList,new Position[]{ Position.AM ,Position.LM, Position.CM , Position.RM , Position.DM } , 3)
                    .stream()
                    .forEach(p->p.setMain(true));
            // 수비수 4명
            decisionMain(playerList,new Position[]{Position.LB ,Position.LWB ,Position.CB, Position.RB , Position.RWB} , 4)
                    .stream()
                    .forEach(p->p.setMain(true));
            // 골기퍼 1명
            decisionMain(playerList,new Position[]{Position.GK} , 1)
                    .stream()
                    .forEach(p->p.setMain(true));
        });

        //처음 등록된 선수들의 스탯을 랜덤을 결정함.
        autoStatConfig.playerStatConfig();
    }
    private List<Player> decisionMain(List<Player> playerList , Position [] positions , int n){
        List<Player> ret = new ArrayList<>();
        for(var player : playerList){
            Position position = player.getPosition();
            for(var p : positions){
                if(position == p) ret.add(player);
            }
        }

        while(true){
            int sz  = ret.size();
            if(sz == n) break;
            int rn = RandomNumber.returnRandomNumber(0,ret.size()-1);
            ret.remove(rn);
        }
        return ret;
    }
    private void directorSetting() throws IOException {
        String pathDirector = filePath +"director.txt";
        BufferedReader cin = new BufferedReader(new FileReader(pathDirector));
        List<Team> teams = teamRepository.findAll();
        int pos = 0;
        while(pos < 64){
            String temp = cin.readLine();
            if(temp == null) break;
            Director director = Director.createDirector(temp);
            director.changeTeam(teams.get(pos));
            directorRepository.save(director);
            pos+=1;
        }
        cin.close();
    }
    private void leagueSeasonSetting(){
        Season season = seasonRepository.findById(1L).orElse(null);
        leagueRoundGenerator.generator(season.getCurrentSeason());
    }
    private void championsSeasonSetting(){
        Season season = seasonRepository.findById(1L).orElse(null);
        championsRoundGenerator.generator(season.getCurrentSeason(),season.getCurrentChampionsRoundSt()); //roundSt = 16이어야함.
    }
}

