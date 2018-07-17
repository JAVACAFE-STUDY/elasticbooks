package example;

import analyzer.CustomLengthAnalyzer;
import service.AnalyzerService;
import util.CsvLoader;
import vo.TweetPost;

import java.util.List;

/**
 * Created by hwang on 2018. 5. 28..
 */
public class CustomLengthAnalyzerExample {
    public static void main(String args[]) throws Exception{
        //CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvUtil = new CsvLoader();
        List<TweetPost> reviewList = csvUtil.readEnglishReview();

        //나만의 분석기를 설정한다
        CustomLengthAnalyzer myAnalyzer = new CustomLengthAnalyzer();
        AnalyzerService analyzerService = new AnalyzerService();

        //나만의 분석기를 테스트한다.
        analyzerService.analyzeText(reviewList, myAnalyzer);
    }
}
