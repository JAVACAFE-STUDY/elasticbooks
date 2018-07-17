package example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.*;
import util.CsvLoader;
import vo.TweetPost;

import java.util.List;

/**
 * Created by hwang on 2018. 6. 3..
 */
public class TermVectorExample {
    public static void main(String args[]) throws Exception{
        //CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvUtil = new CsvLoader();
        List<TweetPost> reviewList = csvUtil.readEnglishReview();

        //Term Vector를 분석한다
        analyzeTermVector(reviewList, new StandardAnalyzer());
    }

    public static void analyzeTermVector(List<TweetPost> reviewList, Analyzer analyzer) throws Exception{

        for(TweetPost post : reviewList){
            if( post.getText() != null ){
                TokenStream tokenStream = analyzer.tokenStream("twitterText", post.getText());

                TypeAttribute typeAttribute = tokenStream.addAttribute(TypeAttribute.class);
                PositionIncrementAttribute positionIncrementAttribute = tokenStream.addAttribute(PositionIncrementAttribute.class);
                PositionLengthAttribute positionLengthAttribute = tokenStream.addAttribute(PositionLengthAttribute.class);
                OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
                TermFrequencyAttribute termFrequencyAttribute = tokenStream.addAttribute(TermFrequencyAttribute.class);
                CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
                TermToBytesRefAttribute termToBytesRefAttribute = tokenStream.addAttribute(TermToBytesRefAttribute.class);
                try {
                    //스트림의 시작을 리셋한다.(필수)
                    tokenStream.reset();
                    System.out.println();
                    while (tokenStream.incrementToken()) {
                        //Token들을 표시한다.
                        System.out.println("typeAttribute : " + typeAttribute.type());
                        System.out.println("positionIncrementAttribute : " + positionIncrementAttribute.getPositionIncrement());
                        System.out.println("positionLengthAttribute : " +positionLengthAttribute.getPositionLength());
                        System.out.println("offsetAttribute : " + offsetAttribute.startOffset());
                        System.out.println("termFrequencyAttribute : " + termFrequencyAttribute.getTermFrequency());
                        System.out.println("charTermAttribute :" + charTermAttribute);
                        System.out.println("termToBytesRefAttribute :" + termToBytesRefAttribute.getBytesRef());
                        System.out.println("===========================================");
                    }
                    tokenStream.end();
                } finally {
                    tokenStream.close();
                }
            }


        }
    }
}
