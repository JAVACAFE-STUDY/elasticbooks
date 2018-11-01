package example.korean;

import org.snu.ids.kkma.ma.MExpression;
import org.snu.ids.kkma.ma.MorphemeAnalyzer;
import org.snu.ids.kkma.ma.Sentence;

import java.util.List;

public class KkmaExample {
    public static void main(String[] args) throws Exception {
        String sentenceString = "마산을 대표하는 음식 중에 하나가 바로 '아구찜'이다. 마산 오동동에 가면 마산의 명물인 아구거리가 형성되어 있는데, 거리의 음식점 중 한 곳이 이곳이다. "
            + "갈분가루를 쓰지 않아 걸죽하지 않다는 이 집만의 양념과 호텔에서의 경력이 밑바탕이 된 철저한 서비스 정신과 친절이 바로 이 곳의 인기비결. 알싸한 양념과 구수한 아구의 고기맛이 제대로 어우러진 마산아구찜의 진수를 맛 볼 수 있는 곳이다.";

        // 형태소 분석기 생성
        MorphemeAnalyzer morphemeAnalyzer = new MorphemeAnalyzer();
        // 로그 사용 안함 설정
        morphemeAnalyzer.createLogger(null);
        // 분석 수행
        List<MExpression> expressions = morphemeAnalyzer.analyze(sentenceString);

        // 결과 정제
        expressions = morphemeAnalyzer.postProcess(expressions);
        expressions = morphemeAnalyzer.leaveJustBest(expressions);

        // 글을 문장으로 분리
        List<Sentence> sentences = morphemeAnalyzer.divideToSentences(expressions);
        for( int i = 0; i < sentences.size(); i++ ) {
            Sentence st = sentences.get(i);
            System.out.println("=============================================  " + st.getSentence());
            for( int j = 0; j < st.size(); j++ ) {
                System.out.println(st.get(j));
            }
        }
        morphemeAnalyzer.closeLogger();
    }
}
