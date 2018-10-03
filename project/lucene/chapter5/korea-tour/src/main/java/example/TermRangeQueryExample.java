package example;

import helper.CsvLoader;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import service.IndexService;
import service.SearchService;
import vo.TourInfo;

import java.util.List;

public class TermRangeQueryExample {
    public static void main(String args[]) throws Exception {
        // CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvHelper = new CsvLoader();
        List<TourInfo> tourInfoList = csvHelper.readTourInfo();

        // Directory를 사용한다.
        Directory index = new RAMDirectory();

        // 색인을 한다.
        IndexService indexService = new IndexService();
        indexService.indexTourInfo(index, tourInfoList);

        // 분석 결과를 확인한다.
        int maxHitCount = 10;

        System.out.println("====================================>> '느티오무' 단어가 포함된 여행정보");
        getTermRangeQuery(index, maxHitCount);

    }

    private static void getTermRangeQuery(Directory index, int maxHitCount) throws Exception{
        TermRangeQuery query = TermRangeQuery.newStringRange("courseName","02코스","04코드", true, true);

        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, query);
    }
}
