package com.example.newscoccer.RegisterService.player;

import com.example.newscoccer.domain.DataTransferObject;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Player.Stat;
import lombok.Data;

/**
 *  Team  vs teamId
 *  Stat vs 개별 속성
 */
@Data
public class PlayerUpdateDto extends DataTransferObject {
    private String name;
    private Long teamId;
    private Position position;
    //피지컬
    private int acceleration; // 가속력
    private int speed;//속력
    private int physicalFight;  //몸싸움

    private int stamina ;// 체력
    private int activeness; // 적극성
    private int jump; //점프력
    private int balance; // 밸런스 - > 넘어지냐 안넘어지냐


    // 패스
    private int ballControl; // 볼컨
    private int crosses;// 크로스

    private int pass; // 패스
    private int longPass; //롱 패스

    //공격력
    private int dribble; //
    private int goalDetermination; // 결정력
    private int midRangeShot; // 중거리슛
    private int shootPower; // 슛파워
    private int heading; // 헤딩

    //수비
    private int defense; //수비력
    private int tackle; // 태클
    private int intercepting; // 가로채기
    private int slidingTackle; // 슬라이딩 태클

    //골기퍼
    private int diving; // 다이빙
    private int handling; // 핸들링
    private int goalKick; //골킥
    private int speedReaction; // 반응속도

    //기타
    private int positioning; // 위치선정
    private int visualRange; // 시야
    private int sense; // 센스


    public void settingData(Player player){
        Stat stat = player.getStat();
        this.name = player.getName();
        this.teamId = player.getTeam().getId();
        this.position = player.getPosition();

        this.acceleration = stat.getAcceleration();
        this.speed = stat.getSpeed();
        this.physicalFight = stat.getPhysicalFight();
        this.stamina = stat.getStamina();
        this.activeness = stat.getActiveness();
        this.jump = stat.getJump();
        this.balance = stat.getBalance();
        this.ballControl = stat.getBallControl();
        this.crosses = stat.getCrosses();
        this.pass = stat.getPass();
        this.longPass = stat.getLongPass();
        this.dribble = stat.getDribble();
        this.goalDetermination = stat.getGoalDetermination();
        this.midRangeShot = stat.getMidRangeShot();
        this.shootPower = stat.getShootPower();
        this.heading = stat.getHeading();
        this.defense = stat.getDefense();
        this.tackle = stat.getTackle();
        this.slidingTackle = stat.getSlidingTackle();
        this.intercepting = stat.getIntercepting();
        this.diving = stat.getDiving();
        this.handling = stat.getHandling();
        this.goalKick = stat.getGoalKick();
        this.speedReaction = stat.getSpeedReaction();
        this.positioning = stat.getPositioning();
        this.visualRange = stat.getVisualRange();
        this.sense = stat.getSense();
    }


}
