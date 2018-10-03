package service;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import vo.TweetPost;

import java.util.List;

public class AnalyzerService {
    public void analyzeText(List<TweetPost> reviewList, Analyzer analyzer) throws Exception{

        System.out.println("\n\n\n###################" + analyzer.getClass().getName() + " Test Result #############################################################");
        for(TweetPost post : reviewList){
            if( post.getText() != null ){
                // TokenStream 생성한다.
                TokenStream tokenStream = analyzer.tokenStream("twitterText", post.getText());
                // Token String을 가져오기 위한 CharTermAttribute 설정
                CharTermAttribute cta = tokenStream.addAttribute(CharTermAttribute.class);
                try {
                    // 스트림의 시작을 리셋한다.(필수)
                    tokenStream.reset();
                    System.out.println();
                    // 토큰을 순차적으로 읽는다
                    while (tokenStream.incrementToken()) {
                        // 토큰을 String Value로 표시한다.
                        System.out.print(cta);
                        System.out.print(" | ");
                    }
                    tokenStream.end(); // 사용을 마친 TokenStream을 종료 처리한다.
                } finally {
                    tokenStream.close();  // 완전 사용이 끝난 TokenStream의 연관 자원을 해제한다.
                }
            }


        }
    }
}
