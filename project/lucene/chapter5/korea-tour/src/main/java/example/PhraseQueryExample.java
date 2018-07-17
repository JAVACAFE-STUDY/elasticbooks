package example;

import helper.CsvLoader;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import service.IndexService;
import service.SearchService;
import vo.TourInfo;

import java.util.List;

/**
 * Created by hwang on 2018. 5. 27..
 */
public class PhraseQueryExample {

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

        System.out.println("====================================>> '마애여래삼존상'와 '개심사' 사이가 하나의 단어가 포함된 여행정보");
        System.out.println("===== position 방식 =====");
        getPhraseQueryUsingPosition(index,  maxHitCount);
        System.out.println("===== slop ㅇ방식 =====");
        getPhraseQueryUsingSetSlop(index, maxHitCount);
    }

    private static void getPhraseQueryUsingPosition(Directory index, int maxHitCount) throws Exception{
        PhraseQuery phraseQuery = new PhraseQuery.Builder()
                .add(new Term("description", "마애여래삼존상"),1)
                .add(new Term("description", "개심사"), 3)
                .build();

        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, phraseQuery);
    }

    private static void getPhraseQueryUsingSetSlop(Directory index, int maxHitCount) throws Exception{
        PhraseQuery phraseQuery = new PhraseQuery.Builder()
            .add(new Term("description", "마애여래삼존상"))
            .add(new Term("description", "개심사"))
            .setSlop(1)
            .build();

        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, phraseQuery);
    }
}
