package com.example.newscoccer.SearchService.director.champions;

import com.example.newscoccer.domain.DataTransferObject;
import com.example.newscoccer.domain.Round.Round;
import com.example.newscoccer.domain.Round.RoundStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Data
public class DirectorChampionsInfoResponse extends DataTransferObject {
    private int rank;
    private int win;
    private int draw;
    private int lose;
    private List<PairRecord> pairRecordList = new ArrayList<>();

    public DirectorChampionsInfoResponse(int rank, int win, int draw, int lose) {
        this.rank = rank;
        this.win = win;
        this.draw = draw;
        this.lose = lose;
    }

    public void addRecord(Round round , String name , String oppositeName, int score , int oppositeScore ){
        boolean isTrue = round.getRoundStatus() == RoundStatus.DONE  ? true : false;
        pairRecordList.add(new PairRecord(isTrue,name, oppositeName,score,oppositeScore ));

    }

    /**
     *  라운드가 끝났는 지
     *  팀 이름 , 상태 팀이름 , 스코어 상대 스코어
     */
    @AllArgsConstructor
    @Getter @Setter
    static class PairRecord{
        private boolean roundDone;
        private String name ;
        private String oppositeName ;
        private int score;
        private int oppositeScore;
    }
}
