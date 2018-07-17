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
                TokenStream tokenStream = analyzer.tokenStream("twitterText", post.getText());
                CharTermAttribute cta = tokenStream.addAttribute(CharTermAttribute.class);
                try {
                    //스트림의 시작을 리셋한다.(필수)
                    tokenStream.reset();
                    System.out.println();
                    while (tokenStream.incrementToken()) {
                        //Token들을 표시한다.
                        System.out.print(cta);
                        System.out.print(" | ");
                    }
                    tokenStream.end();
                } finally {
                    tokenStream.close();
                }
            }


        }
    }
}
