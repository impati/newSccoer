package com.example.newscoccer.auto.Game;

import com.example.newscoccer.domain.Player.Position;
import com.example.newscoccer.support.RandomNumber;

/**
 * Auto 기능에서 사용하는 PositionUtils 
 */
public class AutoPositionUtils {
    
    private static <T> T common(Position p ,PositionFeature<T> feature){

        if (p == Position.ST || p == Position.CF ||
                p == Position.RF || p== Position.LF) {
            return feature.striker();
        }
        else if (p == Position.AM || p == Position.LM ||
                p == Position.CM || p == Position.RM ||
                p == Position.DM) {
            return feature.midfielder();
        }
        else if (p == Position.LB || p == Position.LWB ||
                p == Position.CB || p == Position.RB ||
                p == Position.RWB) {
            return feature.defender();
        }
        else{
            return feature.keeper();
        }

    }

    public  static int passUtil(Position p  , int stat) {
        PositionFeature<Integer> feature = new PositionFeature<>() {
            @Override
            public Integer striker() {
                int ans =  (int)( stat * 0.8);
                int modValue = 5000;
                int s = RandomNumber.returnRandomNumber(0,ans / modValue) + 1;
                int e = RandomNumber.returnRandomNumber(s,s + ans / modValue);
                return RandomNumber.returnRandomNumber(s , e);
            }

            @Override
            public Integer midfielder() {
                int ans =  (int)( stat * 1.1);
                int modValue = 4100;
                int s = RandomNumber.returnRandomNumber(0,ans / modValue) + 3;
                int e = RandomNumber.returnRandomNumber(s,s + ans / modValue);
                return RandomNumber.returnRandomNumber(s , e);
            }

            @Override
            public Integer defender() {
                int ans =  stat;
                int modValue = 4100;
                int s = RandomNumber.returnRandomNumber(0,ans / modValue) + 3;
                int e = RandomNumber.returnRandomNumber(s,s + ans / modValue);
                return RandomNumber.returnRandomNumber(s , e);
            }

            @Override
            public Integer keeper() {
                int ans =  stat;
                int modValue = 10000;
                int s = RandomNumber.returnRandomNumber(0,ans / modValue) ;
                int e = RandomNumber.returnRandomNumber(s,s + ans / modValue);
                return RandomNumber.returnRandomNumber(s / 2 , e / 2);
            }
        };
        return common(p,feature);
    }


    public static int defenseUtil(Position p,int stat){
        PositionFeature<Integer> feature = new PositionFeature<>() {
            @Override
            public Integer striker() {
                int ans =  (int)( stat * 0.7);
                int modValue = 9500;
                int s = RandomNumber.returnRandomNumber(0,ans / modValue) + 1;
                int e = RandomNumber.returnRandomNumber(s,s + ans / modValue);
                return RandomNumber.returnRandomNumber(s , e);
            }

            @Override
            public Integer midfielder() {
                int ans = stat;
                int modValue = 6100;
                int s = RandomNumber.returnRandomNumber(0,ans / modValue) + 3;
                int e = RandomNumber.returnRandomNumber(s,s + ans / modValue);
                return RandomNumber.returnRandomNumber(s , e);
            }

            @Override
            public Integer defender() {
                int ans =  (int)(stat * 1.2);
                int modValue = 5800;
                int s = RandomNumber.returnRandomNumber(0,ans / modValue) + 3;
                int e = RandomNumber.returnRandomNumber(s,s + ans / modValue);
                return RandomNumber.returnRandomNumber(s , e);
            }

            @Override
            public Integer keeper() {
                int ans =  stat;
                int modValue = 12000;
                int s = RandomNumber.returnRandomNumber(0,ans / modValue) ;
                int e = RandomNumber.returnRandomNumber(s,s + ans / modValue);
                return RandomNumber.returnRandomNumber(s / 2 , e / 2);
            }
        };
        return common(p,feature);
    }



    public static int normalGoalUtil(AutoPersonalData ele , Position p , int receivedPass , int stat){
        PositionFeature<Integer> feature = new PositionFeature<>() {
            @Override
            public Integer striker() {
                int ret = 0;
                int count = receivedPass;
                int value = (int)(stat * 1.4);
                while(count != 0){
                    int rn = RandomNumber.returnRandomNumber(0, 1500);
                    int n = RandomNumber.returnRandomNumber(0, value);
                    if(rn * 2 < n) ele.setShooting(ele.getShooting() + 1);
                    if(rn * 3 < n) ele.setValidShooting(ele.getValidShooting() + 1);
                    if (rn * 5 < n) {
                        ret +=1;
                        ele.setGoal(ele.getGoal() + 1);
                    }
                    count -=1;
                }
                return ret;
            }

            @Override
            public Integer midfielder() {
                int ret = 0;
                int count = receivedPass;
                int value = (int)(stat * 1.2);
                while(count != 0){
                    int rn = RandomNumber.returnRandomNumber(0, 1500);
                    int n = RandomNumber.returnRandomNumber(0, value);
                    if(rn * 2 < n) ele.setShooting(ele.getShooting() + 1);
                    if(rn * 3 < n) ele.setValidShooting(ele.getValidShooting() + 1);
                    if (rn * 5 < n) {
                        ret +=1;
                        ele.setGoal(ele.getGoal() + 1);
                    }
                    count -=1;
                }
                return ret;
            }

            @Override
            public Integer defender() {
                int ret = 0;
                int count = receivedPass;
                int value = (int)(stat * 0.3);
                while(count != 0){
                    int rn = RandomNumber.returnRandomNumber(0, 2000);
                    int n = RandomNumber.returnRandomNumber(0, value);
                    if(rn * 2 < n) ele.setShooting(ele.getShooting() + 1);
                    if(rn * 4 < n) ele.setValidShooting(ele.getValidShooting() + 1);
                    if (rn * 6 < n) {
                        ret +=1;
                        ele.setGoal(ele.getGoal() + 1);
                    }
                    count -=1;
                }
                return ret;
            }

            @Override
            public Integer keeper() {
                int ret = 0;
                int count = receivedPass;
                int value = (int)(stat * 0.01);
                while(count != 0){
                    int rn = RandomNumber.returnRandomNumber(0, 1500);
                    int n = RandomNumber.returnRandomNumber(0, value);
                    if(rn * 2 < n) ele.setShooting(ele.getShooting() + 1);
                    if(rn * 4 < n) ele.setValidShooting(ele.getValidShooting() + 1);
                    if (rn * 6 < n) {
                        ret +=1;
                        ele.setGoal(ele.getGoal() + 1);
                    }
                    count -=1;
                }
                return ret;
            }
        };
        return common(p,feature);
    }

    public static int headingAndMidGoalUtil(AutoPersonalData ele , Position p , int receivedPass , int stat){
        PositionFeature<Integer> feature = new PositionFeature<>() {
            @Override
            public Integer striker() {
                int ret = 0;
                int count = receivedPass;
                int value = (int)(stat * 1.2);
                while(count != 0){
                    int rn = RandomNumber.returnRandomNumber(0, 1500);
                    int n = RandomNumber.returnRandomNumber(0, value);
                    if(rn * 3 < n) ele.setShooting(ele.getShooting() + 1);
                    if(rn * 4 < n) ele.setValidShooting(ele.getValidShooting() + 1);
                    if (rn * 7 < n) {
                        ret +=1;
                        ele.setGoal(ele.getGoal() + 1);
                    }
                    count -=1;
                }
                return ret;
            }

            @Override
            public Integer midfielder() {
                int ret = 0;
                int count = receivedPass;
                int value = stat;
                while(count != 0){
                    int rn = RandomNumber.returnRandomNumber(0, 1500);
                    int n = RandomNumber.returnRandomNumber(0, value);
                    if(rn * 3 < n) ele.setShooting(ele.getShooting() + 1);
                    if(rn * 4 < n) ele.setValidShooting(ele.getValidShooting() + 1);
                    if (rn * 7 < n) {
                        ret +=1;
                        ele.setGoal(ele.getGoal() + 1);
                    }
                    count -=1;
                }
                return ret;
            }

            @Override
            public Integer defender() {
                int ret = 0;
                int count = receivedPass;
                int value = (int)(stat * 0.3);
                while(count != 0){
                    int rn = RandomNumber.returnRandomNumber(0, 2000);
                    int n = RandomNumber.returnRandomNumber(0, value);
                    if(rn * 3 < n) ele.setShooting(ele.getShooting() + 1);
                    if(rn * 4 < n) ele.setValidShooting(ele.getValidShooting() + 1);
                    if (rn * 7 < n) {
                        ret +=1;
                        ele.setGoal(ele.getGoal() + 1);
                    }
                    count -=1;
                }
                return ret;
            }

            @Override
            public Integer keeper() {
                int ret = 0;
                int count = receivedPass;
                int value = (int)(stat * 0.01);
                while(count != 0){
                    int rn = RandomNumber.returnRandomNumber(0, 1500);
                    int n = RandomNumber.returnRandomNumber(0, value);
                    if(rn * 3 < n) ele.setShooting(ele.getShooting() + 1);
                    if(rn * 4 < n) ele.setValidShooting(ele.getValidShooting() + 1);
                    if (rn * 7 < n) {
                        ret +=1;
                        ele.setGoal(ele.getGoal() + 1);
                    }
                    count -=1;
                }
                return ret;
            }
        };
        return common(p,feature);
    }




}
