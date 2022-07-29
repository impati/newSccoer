package com.example.newscoccer.springDataJpa.dto;

import com.example.newscoccer.domain.DataTransferObject;
import com.example.newscoccer.domain.Player.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 리그나 챔피언스리그에 참가한 선수들을 Projection 하기위한 dto
 */
@Data
@AllArgsConstructor
public class PlayerParticipate extends DataTransferObject {
    private Player player;
    private Long participateCount ;
}