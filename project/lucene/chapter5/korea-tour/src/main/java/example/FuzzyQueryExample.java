package example;

import helper.CsvLoader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import service.IndexService;
import service.SearchService;
import vo.TourInfo;

import java.util.List;

/**
 * Created by hwang on 2018. 5. 27..
 */
public class FuzzyQueryExample {

    public static void main(String args[]) throws Exception {
        //CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvHelper = new CsvLoader();
        List<TourInfo> tourInfoList = csvHelper.readTourInfo();
        //tourInfoList.forEach(System.out::println);

        //Directory를 사용한다.
        Directory index = new RAMDirectory();

        //색인을 한다.
        IndexService indexService = new IndexService();
        indexService.indexTourInfo(index, tourInfoList);

        //분석 결과를 확인한다.
        int maxHitCount = 10;

        System.out.println("====================================>> '느티오무' 단어가 포함된 여행정보");
        getFuzzyQuery(index, maxHitCount);

    }

    private static void getFuzzyQuery(Directory index, int maxHitCount) throws Exception{
        FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("description", "느티오무"), 1);

        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, fuzzyQuery);
    }
}
