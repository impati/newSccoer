package com.example.newscoccer.SearchService.director.Info;

import com.example.newscoccer.SearchService.common.EntitySimpleInfo;
import com.example.newscoccer.domain.director.Director;
import com.example.newscoccer.springDataJpa.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultDirectorSimpleInfo implements EntitySimpleInfo<DirectorSimpleInfoRequest , DirectorSimpleInfoResponse> {
    private final DirectorRepository  directorRepository;


    /**
     * req , EntityId를 이용하여 감독 정보를 가져온다.
     */
    @Override
    public DirectorSimpleInfoResponse simpleInfo(DirectorSimpleInfoRequest req) {
        Director director = directorRepository.findById(req.getDirectorId()).orElse(null);
        DirectorSimpleInfoResponse resp = new DirectorSimpleInfoResponse();
        resp.setName(director.getName());
        if(director.getTeam() !=null) resp.setTeamName(director.getTeam().getName());
        return  resp;
    }
}
