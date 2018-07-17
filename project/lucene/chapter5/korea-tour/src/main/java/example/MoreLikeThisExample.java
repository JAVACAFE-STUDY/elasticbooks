package example;

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
import vo.TourInfo;

import java.io.StringReader;
import java.util.List;

/**
 * MoreLikeThis 예제
 */
public class MoreLikeThisExample {
    public static void main(String args[]) throws Exception{
        //CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvHelper = new CsvLoader();
        List<TourInfo> tourInfoList = csvHelper.readTourInfo();

        //Directory를 사용한다.
        Directory index = new RAMDirectory();

        //색인을 한다.
        IndexService indexService = new IndexService();
        indexService.indexTourInfo(index, tourInfoList);

        SearchService searchService = new SearchService();
        int maxHitCount = 10;

        //질의할 때, 비슷한 문서(문장)의 정보를 파라미터로 넘긴다
        String queryText = "녹지순환산책로를 접근이 용이하도록 재구성한 코스이다. 따라서 배봉산근린공원이 그 중심을 이룬다. 배봉산근린공원은 산이 부족한 동대문구의 보조 산소통이다. 배봉산은 동대문구 전농동에 있는 표고 110m의 산이다.";

        //MoreLikeThis 결과를 검색한다
        getMoreLikeThis(index, maxHitCount, queryText);
    }


    private static void getMoreLikeThis(Directory index, int maxHitCount, String queryText) throws Exception{
        IndexReader reader = DirectoryReader.open(index);
        MoreLikeThis moreLikeThis = new MoreLikeThis(reader);
        String[] fields = new String[1];
        fields[0] = "description";
        moreLikeThis.setFieldNames(fields);
        moreLikeThis.setAnalyzer(new CustomKoreanAnalyzer());
        Query q = moreLikeThis.like( 5);
        moreLikeThis.like("description", new StringReader(queryText));

        //검색 결과를 출력한다
        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, q);
    }
}
