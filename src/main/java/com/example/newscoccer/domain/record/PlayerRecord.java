package com.example.newscoccer.domain.record;

import com.example.newscoccer.domain.BaseEntity;
import com.example.newscoccer.domain.Player.Player;
import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Team;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
public class PlayerRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="player_id")
    protected Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    protected Team team;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id")
    protected Round round;


    protected int goal;
    protected int assist;
    protected int pass;
    //슈팅
    protected int shooting;
    // 유효 슈팅
    protected int validShooting;
    //파울
    protected int foul;
    //선방
    protected int goodDefense;
    protected int grade;

    // 팀 당 bestPlayer
    protected boolean isBest;

    @Enumerated(EnumType.STRING)
    protected Position position;

    @Enumerated(EnumType.STRING)
    protected MatchResult matchResult;





    // later
    protected double rating;
    protected int rank;


    public static PlayerRecord createPlayerRecord(Player player, Position position, Team team, Round round){
        PlayerRecord PlayerRecord = round.playerRecordReturn();
        PlayerRecord.setPlayer(player);
        PlayerRecord.setRound(round);
        PlayerRecord.setPosition(position);
        PlayerRecord.setTeam(team);
        return PlayerRecord;
    }

    /**
     * 넘겨 받은 경기 결과를 저장.
     */
    public void update(int goal, int assist,int pass,
                       int shooting ,int validShooting,int foul,
                       int defense , int grade,MatchResult matchResult){
        this.goal = goal;
        this.assist = assist;
        this.pass = pass;
        this.shooting = shooting;
        this.validShooting = validShooting;
        this.foul= foul;
        this.goodDefense = defense;
        this.grade = grade;
        this.matchResult = matchResult;
        this.isBest = false;
    }



}
