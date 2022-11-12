package com.example.newscoccer.domain.Round;

import com.example.newscoccer.exception.NotFoundRoundFunction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoundResolver {
    private final List<RoundFunction> roundFunctionList ;
    private final RoundUtils roundUtils;
    public <T> T roundFunction(RoundDto arg ,  Class feature,  Class<T> returnType){
        for(int i = 0;i<roundFunctionList.size();i++){
            RoundFunction function = roundFunctionList.get(i);
            if(feature.isInstance(function)){
                if(function.supports(roundUtils.getRound(arg.getRoundId()))){
                    return function.feature(arg,returnType);
                }
            }
        }
        throw new NotFoundRoundFunction("찾고자하는 라운드 기능이 없습니다.");
    }
    public void roundFunction(RoundDto arg ,  Class feature){
        for(int i = 0;i<roundFunctionList.size();i++){
            RoundFunction function = roundFunctionList.get(i);
            if(feature.isInstance(function)){

                if(function.supports(roundUtils.getRound(arg.getRoundId()))){
                    function.feature(arg) ;
                    return ;
                }
            }
        }
        throw new NotFoundRoundFunction("찾고자하는 라운드 기능이 없습니다.");
    }
}
