package com.example.newscoccer.indenpent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CollectorsToMapTest {
    @Test
    @DisplayName("collectorToMap")
    public void toMap() throws Exception{
        // given

        List<Person> ret = new ArrayList<>();
        for(int i = 1;i<=10;i++) {
            Person person1 = new Person(1L,String.valueOf(i));
            Person person2 = new Person(2L,String.valueOf(i + 10));
            ret.add(person1);
            ret.add(person2);
        }
//        // when
//        Map<Long, Person> collect = ret.stream().collect(Collectors.toMap(Person::getId, Function.identity()));
//        // then
//        for(var ele : collect.keySet()){
//            System.out.println("ele = " + ele);
//        }
//
//        for(var ele : collect.entrySet()){
//            System.out.println("ele = " + ele);
//        }

    }


    static class Person{
        public Person(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        private Long id;
        private String name;
        public Long getId(){
            return id;
        }
        public String getName(){
            return name;
        }
    }
}
