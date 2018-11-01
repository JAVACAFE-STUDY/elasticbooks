package example;

import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import service.AnalyzerService;
import util.CsvLoader;
import vo.TweetPost;

import java.util.List;

public class TwitterKoreanTextAnalysisMain {

	public static void main(String[] args) throws Exception{
		// CSV 파일로 부터 데이터를 읽어온다.
		CsvLoader csvUtil = new CsvLoader();
		List<TweetPost> reviewList = csvUtil.readKoreanReview();

		AnalyzerService analyzerService = new AnalyzerService();
		// StandardAnalyzer를 테스트한다.
		analyzerService.analyzeText(reviewList, new KoreanAnalyzer());

	}

}
