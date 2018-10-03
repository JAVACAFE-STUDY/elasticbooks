package example.morelikethis;

import analyzer.CustomKoreanAnalyzer;
import helper.CsvLoader;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import service.IndexService;
import service.SearchService;
import vo.RestaurantInfoVo;

import java.io.StringReader;
import java.util.List;

/**
 * MoreLikeThis 예제
 */
public class MoreLikeThisExample {
    public static void main(String args[]) throws Exception{
        // CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvHelper = new CsvLoader();
        List<RestaurantInfoVo> tourInfoList = csvHelper.readRestaurantnfo();

        // Directory를 사용한다.
        Directory index = new RAMDirectory();

        // 색인을 한다.
        IndexService indexService = new IndexService();
        indexService.indexRestaurantInfo(index, tourInfoList);

        int maxHitCount = 10;

        // 질의할 때, 비슷한 문서(문장)의 정보를 파라미터로 넘긴다
        String queryText = "박속밀국낙지탕은 꽃게장, 게꾹지, 우럭젓국찌개 등과 함께 태안의 향토음식 중 하나로서 박과 야채를 넣어 끓인 국물에 낙지를 넣어 살짝 익혀 먹은 후 그 육수에 수제비나 칼국수를 넣어 함께 먹는 음식으로 시원하고 맑은 국물과 쫄깃한 낙지의 조화가 일품이다.";

        // MoreLikeThis 결과를 검색한다
        getMoreLikeThis(index, maxHitCount, queryText);
    }


    private static void getMoreLikeThis(Directory index, int maxHitCount, String queryText) throws Exception{
        IndexReader reader = DirectoryReader.open(index);
        // MoreLikeThis 객체 생성
        MoreLikeThis moreLikeThis = new MoreLikeThis(reader);
        String[] fields = new String[1];
        fields[0] = "description";
        // MoreLikeThis 객체 설정
        moreLikeThis.setFieldNames(fields); // 유사도 검색하고자 하는 필드 배열
        moreLikeThis.setAnalyzer(new CustomKoreanAnalyzer());   // 텀벡터를 만들기 위한 Analyzer 설정
        Query q = moreLikeThis.like( 5);
        moreLikeThis.like("description", new StringReader(queryText));

        // 검색 결과를 출력한다
        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, q);
    }
}
