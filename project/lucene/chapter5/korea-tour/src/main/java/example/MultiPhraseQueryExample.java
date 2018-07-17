package example;

import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import helper.CsvLoader;
import service.IndexService;
import service.SearchService;
import vo.TourInfo;

public class MultiPhraseQueryExample {
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

        System.out.println("====================================>> '서해안의'과 '체험하는' 혹은 '서해안의'과 '해돋이가' 사이가 하나의 단어가 포함된 여행정보");
        getMultiPhraseQueryn(index,  maxHitCount);
    }

    private static void getMultiPhraseQueryn(Directory index, int maxHitCount) throws Exception{
        Term[] term2 = {new Term("description", "체험하는"), new Term("description", "해돋이가"), new Term("description", "석양을")};
        MultiPhraseQuery multiPhraseQuery = new MultiPhraseQuery.Builder()
            .add(new Term("description","서해안의"))
            .add(term2)
            .setSlop(1)
            .build();

        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, multiPhraseQuery);
    }
}
