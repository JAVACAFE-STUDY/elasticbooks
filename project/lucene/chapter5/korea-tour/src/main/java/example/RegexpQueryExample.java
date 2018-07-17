package example;

import helper.CsvLoader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.RegexpQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import service.IndexService;
import service.SearchService;
import vo.TourInfo;

import java.util.List;

/**
 * Created by hwang on 2018. 5. 27..
 */
public class RegexpQueryExample {
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

        System.out.println("====================================>> '해안' 단어가 포함된 여행정보");
        getRegexpQuery(index, maxHitCount);
    }

    private static void getRegexpQuery(Directory index, int maxHitCount) throws Exception{
        RegexpQuery regexpQuery = new RegexpQuery(new Term("description", "해안*"));
        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, regexpQuery);
    }

}
