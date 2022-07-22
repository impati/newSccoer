package com.example.newscoccer.SearchService.director.totalInfo;

import com.example.newscoccer.SearchService.common.RankInfo;
import com.example.newscoccer.domain.DataTransferObject;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class DirectorTotalInfoResponse extends RankInfo {
    /**
     * 전체  just 승, 무 , 패 의 합산
     */
    private int win;
    private int draw;
    private int lose;

}

