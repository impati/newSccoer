package com.example.newscoccer.domain.Round;

import com.example.newscoccer.springDataJpa.RoundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 라운드 기능에서 사용하는 기능을 집합
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoundUtils {
    private final RoundRepository roundRepository;
    public Round getRound(Long roundId) {
        return roundRepository.findById(roundId).orElse(null);
    }
    public <T> T ConvertRequestType(Object obj , Class<T> requestType){
        return (T)obj;
    }
}
