package com.example.newscoccer.auto.Game;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class DefaultAutoGameRegister implements AutoGameRegister{

    @Override
    public void autoGameRegister() {

    }
}
