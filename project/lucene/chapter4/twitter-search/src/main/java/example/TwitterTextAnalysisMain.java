package example;

import org.apache.lucene.analysis.core.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import service.AnalyzerService;
import util.CsvLoader;
import vo.TweetPost;

import java.util.List;


/**
 * 트위터 텍스트를 형태소 분석을 한다.
 */
public class TwitterTextAnalysisMain {
    public static void main(String args[]) throws Exception{
        // CSV 파일에서 데이터를 읽는다.
        CsvLoader csvUtil = new CsvLoader();
        List<TweetPost> reviewList = csvUtil.readEnglishReview();

        // 분석기 서비스를 생성한다.
        AnalyzerService analyzerService = new AnalyzerService();

        // StandardAnalyzer를 테스트한다.
        analyzerService.analyzeText(reviewList, new StandardAnalyzer());

        // SimpleAnalyzer를 테스트한다.
        analyzerService.analyzeText(reviewList, new SimpleAnalyzer());

        // WhitespaceAnalyzer를 테스트한다.
        analyzerService.analyzeText(reviewList, new WhitespaceAnalyzer());

        // StopAnalyzer를 테스트한다.
        analyzerService.analyzeText(reviewList, new StopAnalyzer());

        // KeywordAnalyzer를 테스트한다.
        analyzerService.analyzeText(reviewList, new KeywordAnalyzer());

    }
}
