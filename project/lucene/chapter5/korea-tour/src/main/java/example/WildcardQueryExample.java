package example;

import helper.CsvLoader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import service.IndexService;
import service.SearchService;
import vo.TourInfo;

import java.util.List;

/**
 * Created by hwang on 2018. 5. 27..
 */
public class WildcardQueryExample {

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

        System.out.println("====================================>> '조선' 단어가 포함된 여행정보");
        getWildcardQuery(index, maxHitCount);

    }

    private static void getWildcardQuery(Directory index, int maxHitCount) throws Exception{
        WildcardQuery wildcardQuery = new WildcardQuery(new Term("description", "조선*"));
        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, wildcardQuery);
    }


}
