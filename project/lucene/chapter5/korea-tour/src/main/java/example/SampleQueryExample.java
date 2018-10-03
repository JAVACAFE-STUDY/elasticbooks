package example;

import helper.CsvLoader;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import service.IndexService;
import service.SearchService;
import vo.TourInfo;

import java.util.List;

/**
 * 루씬 쿼리에 대한 전반적인 샘플 예제
 */
public class SampleQueryExample {
    public static void main(String args[]) throws Exception{
        // CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvHelper = new CsvLoader();
        List<TourInfo> tourInfoList = csvHelper.readTourInfo();

        // Directory를 사용한다.
        Directory index = new RAMDirectory();

        // 색인을 한다.
        IndexService indexService = new IndexService();
        indexService.indexTourInfo(index, tourInfoList);

        int maxHitCount = 10;

        System.out.println("====================================>> 1. 여행코스 설명에서 '해변'이라는 텀이 있는 문서를 QueryParser 객체를 사용해 검색한다.");
        getQueryParser(index, maxHitCount);

        System.out.println("====================================>> 2. 여행코스 설명에서 '벚꽃'이라는 텀이 있는 문서를 TermQuery 객체를 사용해 검색한다.");
        getTermQuery(index, maxHitCount);

        System.out.println("====================================>> 3. 여행코스의 소요시간 1~3시간인 여행정보를 PointRangeQuery 객체를 사용해 검색한다.");
        getPointRangeQuery(index, maxHitCount);

        System.out.println("====================================>> 4. 여행코스의 설명에서 '마애여래삼존상'와 '개심사' 사이가 하나의 단어가 포함된 여행정보를 검색한다.");
        getPhraseQueryUsingPosition(index, maxHitCount);

        System.out.println("====================================>> 5.여행코스의 설명에서 단어의 앞에 '자연'이라는 단어가 포함된 여행정보를 PrefixQuery객체를 사용해 검색한다.");
        getPrefixQuery(index, maxHitCount);

        System.out.println("====================================>> 6. 여행코스의 설명에서 '조선'으로 시작하는 단어를 WildcardQuery 객체를 사용하여 검색한다.");
        getWildcardQuery(index, maxHitCount);

        System.out.println("====================================>> 7. 여행코스의 설명에서 '느티오무아이'와 유사한 단어가 포함된 정보를 FuzzyQuery 객체를 사용하여 검색한다.");
        getFuzzyQuery(index, maxHitCount);

        System.out.println("====================================>> 8. 여행 코스의 설명에서 RegexpQuery를 이용하여 '해안*\"' 정규식과 일치하는 문서를  RegexpQuery 객체를 이용하여 검색한다.");
        getRegexpQuery(index, maxHitCount);
    }

    /**
     * QueryParser 예
     * @param index
     * @param maxHitCount
     * @throws Exception
     */
    private static void getQueryParser(Directory index, int maxHitCount) throws Exception{
        KoreanAnalyzer analyzer = new KoreanAnalyzer();

        QueryParser qp = new QueryParser("description", analyzer);
        Query queryParser = qp.parse("해변");

        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, queryParser);
    }


    /**
     * TermQuery 예
     * @param index
     * @param maxHitCount
     * @throws Exception
     */
    private static void getTermQuery(Directory index, int maxHitCount) throws Exception{
        TermQuery termQuery = new TermQuery(new Term("description", "벚꽃"));
        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, termQuery);
    }

    /**
     * PointRangeQuery 예
     * @param index
     * @param maxHitCount
     * @throws Exception
     */
    private static void getPointRangeQuery(Directory index, int maxHitCount) throws Exception{
        Query query = DoublePoint.newRangeQuery("hour", 1d, 3d);
        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, query);
    }

    /**
     * PhraseQuery 예
     * @param index
     * @param maxHitCount
     * @throws Exception
     */
    private static void getPhraseQueryUsingPosition(Directory index, int maxHitCount) throws Exception{
        PhraseQuery phraseQuery = new PhraseQuery.Builder()
                .add(new Term("description", "마애여래삼존상"),1)
                .add(new Term("description", "개심사"), 3)
                .build();

        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, phraseQuery);
    }


    /**
     * PrefixQuery 예제
     * @param index
     * @param maxHitCount
     * @throws Exception
     */
    private static void getPrefixQuery(Directory index, int maxHitCount) throws Exception{
        PrefixQuery prefixQuery = new PrefixQuery(new Term("description","자연"));
        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, prefixQuery);
    }


    /**
     * WildcardQuery 예
     * @param index
     * @param maxHitCount
     * @throws Exception
     */
    private static void getWildcardQuery(Directory index, int maxHitCount) throws Exception{
        WildcardQuery wildcardQuery = new WildcardQuery(new Term("description", "조선*"));
        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, wildcardQuery);
    }

    /**
     * FuzzyQuery 예
     * @param index
     * @param maxHitCount
     * @throws Exception
     */
    private static void getFuzzyQuery(Directory index, int maxHitCount) throws Exception{
        FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("description", "느티오무"), 1);

        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, fuzzyQuery);
    }

    /**
     * RegexpQuery 예
     * @param index
     * @param maxHitCount
     * @throws Exception
     */
    private static void getRegexpQuery(Directory index, int maxHitCount) throws Exception{
        RegexpQuery regexpQuery = new RegexpQuery(new Term("description", "해안*"));
        SearchService searchService = new SearchService();
        searchService.getQueryResult(index, maxHitCount, regexpQuery);
    }

}
