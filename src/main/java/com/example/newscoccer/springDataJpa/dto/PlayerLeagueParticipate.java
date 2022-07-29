package com.example.newscoccer.springDataJpa.dto;

import com.example.newscoccer.domain.DataTransferObject;
import com.example.newscoccer.domain.Player.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerLeagueParticipate extends DataTransferObject {
    private Player player;
    private Long participateCount ;
}