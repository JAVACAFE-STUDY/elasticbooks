package example;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

public class StopFilterPositionIncrementAttributeExample {
    public static void main(String[] args) throws Exception{
        String s = "blue is the sky";

        // StandardAnalyzer 생성
        StandardAnalyzer analyzer = new StandardAnalyzer();

        // TokenStream 생성
        TokenStream tokenStream = analyzer.tokenStream("string", s);

        // 토큰 값을 확인하기 위한 CharTermAttribute 설정
        CharTermAttribute cta = tokenStream.addAttribute(CharTermAttribute.class);

        // PositionIncrement를 확인하기 위한 PositionIncrementAttribute 설정
        PositionIncrementAttribute pa = tokenStream.addAttribute(PositionIncrementAttribute.class);
        try {
            // TokenStream 초기화
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                // 토큰을 표시한다.
                System.out.println("CharTerm: " + cta
                    + " | " + "PositionIncrement: " + pa.getPositionIncrement());
            }
            // TokenStream 종료
            tokenStream.end();
        } finally {
            // TokenStream 리소스 반환
            tokenStream.close();
        }
    }
}
