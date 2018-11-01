package example.customanalyzer;

import analyzer.MyAnalyzer;
import helper.CsvLoader;
import service.AnalyzerService;
import vo.RestaurantInfoVo;

import java.util.List;

/**
 * Custom 분석기 사용 예제
 */
public class MyAnalyzerExample {
    public static void main(String args[]) throws Exception{
        // CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvHelper = new CsvLoader();
        List<RestaurantInfoVo> restaurantInfoVoList = csvHelper.readRestaurantnfo();

        // 나만의 분석기를 설정한다
        MyAnalyzer myAnalyzer = new MyAnalyzer();

        // 나만의 분석기를 테스트한다.
        AnalyzerService analyzerService = new AnalyzerService();
        analyzerService.analyzeText(restaurantInfoVoList, myAnalyzer);
    }
}
