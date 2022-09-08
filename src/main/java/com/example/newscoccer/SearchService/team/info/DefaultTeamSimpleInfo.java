package com.example.newscoccer.SearchService.team.info;

import com.example.newscoccer.SearchService.common.EntitySimpleInfo;
import com.example.newscoccer.domain.Team;
import com.example.newscoccer.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * 팀의 기본 정보
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultTeamSimpleInfo implements EntitySimpleInfo<TeamSimpleInfoRequest , TeamSimpleInfoResponse> {
    private final TeamRepository teamRepository;
    @Override
    public TeamSimpleInfoResponse simpleInfo(TeamSimpleInfoRequest req) {
        Team team = teamRepository.findById(req.getTeamId()).orElse(null);
        if(team == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        TeamSimpleInfoResponse resp = new TeamSimpleInfoResponse(team.getName(),team.getLeague().getName(),team.getRating());
        return resp;
    }
}
