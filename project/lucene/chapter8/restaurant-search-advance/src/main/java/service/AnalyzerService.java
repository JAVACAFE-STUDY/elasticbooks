package service;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import vo.RestaurantInfoVo;

import java.util.List;

public class AnalyzerService {
    public void analyzeText(List<RestaurantInfoVo> restaurantInfoList, Analyzer analyzer) throws Exception{

        System.out.println("\n\n\n###################" + analyzer.getClass().getName() + " Test Result #############################################################");
        for(RestaurantInfoVo restaurantInfoVo : restaurantInfoList){
            if( restaurantInfoVo.getDescription() != null ){
                // 토큰스트림 생성
                TokenStream tokenStream = analyzer.tokenStream("description", restaurantInfoVo.getDescription());
                // Token String을 가져오기 위한 CharTermAttribute 설정
                CharTermAttribute cta = tokenStream.addAttribute(CharTermAttribute.class);
                try {
                    // 토큰스트림의 시작을 리셋한다.(필수)
                    tokenStream.reset();
                    System.out.println();
                    // Token을 순차적으로 읽는다
                    while (tokenStream.incrementToken()) {
                        // Token들을 String Value를 표시한다.
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
