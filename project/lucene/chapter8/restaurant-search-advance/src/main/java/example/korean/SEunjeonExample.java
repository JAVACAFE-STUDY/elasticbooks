package example.korean;

import java.util.Arrays;

import org.bitbucket.eunjeon.seunjeon.Analyzer;
import org.bitbucket.eunjeon.seunjeon.Eojeol;
import org.bitbucket.eunjeon.seunjeon.LNode;

public class SEunjeonExample {
    public static void main(String[] args) {
        String sentenceString = "마산을 대표하는 음식 중에 하나가 바로 '아구찜'이다. 마산 오동동에 가면 마산의 명물인 아구거리가 형성되어 있는데, 거리의 음식점 중 한 곳이 이곳이다. "
            + "갈분가루를 쓰지 않아 걸죽하지 않다는 이 집만의 양념과 호텔에서의 경력이 밑바탕이 된 철저한 서비스 정신과 친절이 바로 이 곳의 인기비결. 알싸한 양념과 구수한 아구의 고기맛이 제대로 어우러진 마산아구찜의 진수를 맛 볼 수 있는 곳이다.";

        // 형태소 분석
        System.out.println("======== 형태소 분석 ========");
        for (LNode node : Analyzer.parseJava(sentenceString)) {
            System.out.println(node.morpheme());

        }

        // 어절 분석
        System.out.println("======== 어절 분석 ========");
        for (Eojeol eojeol: Analyzer.parseEojeolJava(sentenceString)) {
            System.out.println("어절 : ");
            for (LNode node: eojeol.nodesJava()) {
                System.out.println(node.morpheme());
            }
        }


        System.out.println("======== 사용자 사전 추가 ========");
        /*
         * 사용자 사전 추가
         * surface,cost
         *   surface: 단어명. '+' 로 복합명사를 구성할 수 있다.
         *           '+'문자 자체를 사전에 등록하기 위해서는 '\+'로 입력. 예를 들어 'C\+\+'
         *   cost: 단어 출연 비용. 작을수록 출연할 확률이 높다.
         */
        Analyzer.setUserDict(Arrays.asList("케크케").iterator());
        for (LNode node : Analyzer.parseJava("케크케가 맛있다")) {
            System.out.println(node.morpheme());
        }

        // 활용어 원형
        System.out.println("======== 활용어 원형 ========");
        for (LNode node : Analyzer.parseJava("빨라짐")) {
            System.out.println("빨라짐 원형");
            for (LNode node2: node.deInflectJava()) {
                System.out.println(node2.morpheme());
            }
        }

        // 복합명사 분해
        System.out.println("======== 복합명사 분해 ========");
        for (LNode node : Analyzer.parseJava("아구찜")) {
            System.out.println("복합명사\n" + node.morpheme());   // 낄끼빠빠
            System.out.println("분해결과");
            for (LNode node2: node.deCompoundJava()) {
                System.out.println(node2.morpheme());  // 낄끼+빠빠
            }
        }
    }
}
