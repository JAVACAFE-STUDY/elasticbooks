package example;

import helper.CsvLoader;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import service.IndexService;
import service.SearchService;
import vo.TourInfo;

import java.util.List;

/**
 * Created by hwang on 2018. 5. 27..
 */
public class QueryParserExample {
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

        System.out.println("====================================>> '해변'과 '시골'이 포함되고 '산이 포함되지 않은 여행정보");
        getQueryParser(index, maxHitCount);

    }

    private static void getQueryParser(Directory index, int maxHitCount) throws Exception{
        KoreanAnalyzer analyzer = new KoreanAnalyzer();

        QueryParser qp = new QueryParser("description", analyzer);
        Query queryParser = qp.parse("해변 AND 시골 NOT 산");

        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, queryParser);
    }
}
