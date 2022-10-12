package bside.weo.plant.config;

public class RandomNickname {
    /**
     * 임시 닉네임 생성
     * @return
     */
    public static String Nickname() {
        String result = "";
        String[] firstDepth = {"행복한", "유쾌한", "밝은", "따뜻한", "다정한"
                , "명랑한", "용감한", "힐링하는", "너그러운", "사교적인"
                , "슬기로운", "현명한", "성실한", "외향적인", "귀여운"
                , "긍적적인", "매력적인", "솔직한", "우아한", "싱그러운"};

        String[] secondDepth = {"스킨답서스", "스파티필름", "몬스테라", "산세베리아", "레티지아"
                , "제라니움", "콩고", "파키라", "피토니아", "싱고니움"
                , "틸란드시아", "아이비", "포인세티아", "베고니아", "칼라디움"
                , "안스리움", "커피나무", "시서스", "레몬라임", "행운목"};


        double firstRandom = Math.floor(Math.random() * (firstDepth.length - 1));
        int firstNum = (int)firstRandom;

        double secondRandom = Math.floor(Math.random() * (secondDepth.length - 1));
        int secondNum = (int)secondRandom;

        result = firstDepth[firstNum] + secondDepth[secondNum];
        return result;
    }
}
