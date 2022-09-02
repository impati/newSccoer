package com.example.newscoccer.domain.record;

import com.example.newscoccer.domain.BaseEntity;
import com.example.newscoccer.domain.Round.Round;
import lombok.*;

import javax.persistence.*;

/**
 * 어시스터  아이디가 0인 경우 "없음"
 */
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of ={"goalPlayerId","assistPlayerId","goalType"})
public class Duo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "duo_id")
    private Long id;

    private Long goalPlayerId;
    private Long assistPlayerId;

    @Enumerated(EnumType.STRING)
    private GoalType goalType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="round_Id")
    private Round round;

    public static Duo create(Long goalPlayer,Long assistPlayer,GoalType goalType,Round round){
        Duo duo = new Duo();
        duo.setAssistPlayerId(assistPlayer);
        duo.setGoalPlayerId(goalPlayer);
        duo.setGoalType(goalType);
        duo.setRound(round);
        return duo;
    }
}
