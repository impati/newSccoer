package com.example.newscoccer.domain.record;

import com.example.soccerleague.domain.BaseEntity;
import com.example.soccerleague.domain.Round.Round;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of ={"goalPlayerId","assistPlayerId","goalType"})
public class Duo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couple_id")
    private Long id;

    private Long goalPlayerId;
    private Long assistPlayerId;

    @Enumerated(EnumType.STRING)
    private GoalType goalType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="round_Id")
    private Round round;

    public static Duo create(Long goalPlayerId,Long assistPlayerId,GoalType goalType,Round round){
        Duo duo = new Duo();
        duo.setGoalPlayerId(goalPlayerId);
        duo.setAssistPlayerId(assistPlayerId);
        duo.setGoalType(goalType);
        duo.setRound(round);
        return duo;
    }
}
