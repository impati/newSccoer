package com.example.newscoccer.support;

public  class RandomNumber {
    public static int returnRandomNumber(int s,int e){
        return (int)(Math.random()*(e - s + 1))+s;
    }

    public static double returnRandomNumber(double s, double e){
        return (Math.random()*(e - s + 1))+s;
    }
}
