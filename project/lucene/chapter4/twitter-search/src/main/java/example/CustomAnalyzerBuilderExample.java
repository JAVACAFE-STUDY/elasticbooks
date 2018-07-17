package example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import service.AnalyzerService;
import util.CsvLoader;
import vo.TweetPost;

import java.util.List;

/**
 * Custom 분석기 사용 예제
 */
public class CustomAnalyzerBuilderExample {
    public static void main(String args[]) throws Exception{

        //CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvUtil = new CsvLoader();
        List<TweetPost> reviewList = csvUtil.readEnglishReview();

        //나만의 분석기를 만든다.
        Analyzer yoloAnalyzer = CustomAnalyzer.builder()
                .withTokenizer(StandardTokenizerFactory.class)
                .addTokenFilter(StandardFilterFactory.class)
                .addTokenFilter(LowerCaseFilterFactory.class)
                .addTokenFilter(ASCIIFoldingFilterFactory.class)
                .addTokenFilter(StopFilterFactory.class, "ignoreCase", "false")
                .build();

        AnalyzerService analyzerService = new AnalyzerService();

        //나만의 분석기를 테스트한다.
        analyzerService.analyzeText(reviewList, yoloAnalyzer);

    }



}
