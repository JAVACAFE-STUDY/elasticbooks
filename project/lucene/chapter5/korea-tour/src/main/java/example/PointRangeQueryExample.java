package example;

import helper.CsvLoader;
import org.apache.lucene.document.DoublePoint;
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
public class PointRangeQueryExample {
    public static void main(String args[]) throws Exception {
        //CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvHelper = new CsvLoader();
        List<TourInfo> tourInfoList = csvHelper.readTourInfo();

        //Directory를 사용한다.
        Directory index = new RAMDirectory();

        //색인을 한다.
        IndexService indexService = new IndexService();
        indexService.indexTourInfo(index, tourInfoList);

        //분석 결과를 확인한다.
        int maxHitCount = 10;

        System.out.println("====================================>> 소요시간 1~3시간의 여행정보");
        getPointRangeQuery(index, maxHitCount);

    }

    private static void getPointRangeQuery(Directory index, int maxHitCount) throws Exception{
        Query query = DoublePoint.newRangeQuery("hour", 1d, 3d);
        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, query);
    }

}
