package com.example.newscoccer.SearchService.common;
/**
 * 선수 , (리그 ,챔피언스 , 유로파 ) 기록
 * 감독 , (리그 ,챔피언스 , 유로파 ) 기록
 *  팀 , (리그 ,챔피언스 , 유로파  기록
*/
 public interface EntityTotalInfo <P,R>{
    R totalInfo(P req);
}
