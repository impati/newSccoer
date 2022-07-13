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

