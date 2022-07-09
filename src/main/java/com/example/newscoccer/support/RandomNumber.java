package com.example.newscoccer.support;

public  class RandomNumber {
    static  int returnRandomNumber(int s,int e){
        return (int)(Math.random()*(e - s + 1))+s;
    }

    static double returnRandomNumber(double s, double e){
        return (Math.random()*(e - s + 1))+s;
    }
}
