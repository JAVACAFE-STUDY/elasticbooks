package example;

import helper.CsvLoader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import service.IndexService;
import service.SearchService;
import vo.TourInfo;

import java.util.List;

/**
 * Created by hwang on 2018. 5. 27..
 */
public class BooleanQueryExample {
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

        System.out.println("====================================>> 해변, 설악산 텀이 포함된 여행정보");
        getBooleanQuery(index, maxHitCount);
    }


    private static void getBooleanQuery(Directory index, int maxHitCount) throws Exception{

        TermQuery termQuery = new TermQuery(new Term("description", "해변"));
        TermQuery termQuery2 = new TermQuery(new Term("description", "설악산"));

        BooleanQuery booleanQuery = new BooleanQuery.Builder()
                .add(new BooleanClause(termQuery, BooleanClause.Occur.SHOULD))
                .add(new BooleanClause(termQuery2, BooleanClause.Occur.SHOULD))
                .build();

        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, booleanQuery);
    }
}
