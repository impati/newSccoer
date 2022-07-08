package com.example.newscoccer.domain.Round;

import com.example.newscoccer.domain.BaseEntity;
import com.example.newscoccer.domain.record.*;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Round extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="round_Id")
    private Long id;

    protected int season;
    protected int roundSt;

    @Enumerated(EnumType.STRING)
    protected RoundStatus roundStatus;





    public PlayerRecord playerRecordReturn(){
            RoundTemplate roundTemplate = new RoundTemplate();
            RoundFeature<PlayerRecord> roundFeature = new RoundFeature<PlayerRecord>() {
                @Override
                public PlayerRecord leagueSolved() {
                    return new PlayerLeagueRecord();
                }

                @Override
                public PlayerRecord championsSolved() {
                    return new PlayerChampionsRecord();
                }
            };
            return roundTemplate.action(this,roundFeature);
    }
    public TeamRecord  teamRecordReturn(){
        RoundTemplate roundTemplate = new RoundTemplate();
        RoundFeature<TeamRecord> roundFeature = new RoundFeature<TeamRecord>() {
            @Override
            public TeamRecord leagueSolved() {
                return new TeamLeagueRecord();
            }
            @Override
            public TeamRecord championsSolved() {
                return new TeamChampionsRecord();
            }
        };
        return roundTemplate.action(this,roundFeature);
    }

}
