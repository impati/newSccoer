# Hot Potato

---

### 1. LeagueRoundGenerator + ChampionsRoundGenerator .... vs  RoundGenerator
    - 1. 각 기능별로 인터페이스를 구현할지. ->기능마다 구현 클래스 , 다형성을 이용하지 못함.
    - 2. 조금 더 추상화된 인터페이스를 구현하여 다형성을 제공할지 . -> 하나의 구현클래스에서 round 별로 기능을 구현.  
    - 3. RoundGenerator를 상속받은 기능별 인터페이스를 구현할지. ->  2번을 이용하면서 1번도 이용할 수 있으나 추상화 비용이 든다.

 >> 1번 선택. 생명주기도 조금 다르고 기능도 다름 , 다형성을 이용하기에도 애매하다. (ex Round 로 구분을 하는데  라운드를 생성해야되는 기능이기 때문에.)


### 2. Round 의 if ,else if .. 문제
>> 현재 : round 도메인에 몰아넣음 . , + roundTemplate 클래스 ,  RoundFeature 인터페이스 로 관리 중.
   
### 3.Season 정보를 어떻게 관리할 것 인가.
 >> 현재 : Season 테이블로 관리를 하고 있으며 튜플 수는 항상 1, id = 1L 


### 4.감독의 기록 :  별도의 감독 기록 vs  팀 기록에서 감독을 FK 로 추가
 >> 현재 : 팀 기록에서 감독을 FK 로 추가 ; 감독 기록과 팀 기록은 사이클 등의 생명 주기가 동일하며 
 감독의 기록에서 얻을 수 있는 정보를 대부분 팀의 기록에서 얻을 수 있다.


### 5. interface <P,R> p->requestDto ,  R->responseDto 
 >> param , return 값을 인터페이스에 의존적인 dto 로 정의해서 사용하면 
 >  유지보수 측면에서 좋고 ,변경에 유연하며 프로젝트 전반적인 기능에 대해 직관성도 기대해 볼 수 있다 .
 > 하지만 기능에 대해 추상화비용이 많이 요구된다는 단점이 있다. 
 > 아주 간단한 param ,return 값일 경우에는 이 방법이 과하며 
 > 그렇다고  프로젝트 전반적인 일관성을 유지할 수 없게 되면 이러한 방법을 사용하는 의미가 없어지는 것이 우려된다.
 > 현재 : req,resp 를 모두 둔다.
 

### 6. 서비스 게층에서 속성값을 주입받고 entity 생성 VS dto 주입받고 entity 생성 db에 저장 

 >> 간단한 경우 전자  , 복잡한 경우 후자 
 >> 대게는 후자를 선택함.
 
### 7.   반복을 해결할 수 있지 않을까 ? 
#### 1.선수 리그 ,챔피언스리그 , 유로파 감독 리그 , 
#### 2.챔피언스리그 , 유로파 
#### 3.팀 리그 , 챔피언스리그 , 유로파 

### 8.   분기 후 쿼리 Vs 쿼리에서 분기 Vs 쿼리 후 분기
>> 일단은 분기 후 쿼리 혹은 쿼리 후 분기 리팩토링 시 쿼리에서 분기할 것.(간단한 경우)

### 9. 자동 기능 다루기  
 1. 라인업을 자동으로 저장하는 기능
 2. 게임 결과를 자동으로 저장하는 기능
 3. 골-어시 pair 를 자동으로 저장하는 기능
 4. 평점 자동 결정 기능
 5. 레이딩 자동 결정 기능

 + 스탯 결정 기능


### 10. 템블릿 안에서 코드 실행
>>  AOP , 전략 패턴

### 11. 코드안에 특정 성격의 코드가 들어가야 하는 경우
>> ??