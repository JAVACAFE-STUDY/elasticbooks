package example;

import analyzer.MyAnalyzer;
import service.AnalyzerService;
import util.CsvLoader;
import vo.TweetPost;

import java.util.List;

/**
 * Custom 분석기 사용 예제
 */
public class MyAnalyzerExample {
    public static void main(String args[]) throws Exception{
        //CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvUtil = new CsvLoader();
        List<TweetPost> reviewList = csvUtil.readEnglishReview();

        //나만의 분석기를 설정한다
        MyAnalyzer myAnalyzer = new MyAnalyzer();

        //나만의 분석기를 테스트한다.
        AnalyzerService analyzerService = new AnalyzerService();
        analyzerService.analyzeText(reviewList, myAnalyzer);
    }
}
