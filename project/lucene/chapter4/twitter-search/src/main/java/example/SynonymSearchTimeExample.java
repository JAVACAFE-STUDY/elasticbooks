package example;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.CharsRef;

import analyzer.SynonymAnalyzer;
import service.IndexService;
import service.SearchService;
import util.CsvLoader;
import vo.TweetPost;

import java.util.List;

public class SynonymSearchTimeExample {
    public static void main(String args[]) throws Exception{
        // CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvUtil = new CsvLoader();

        // 영문 날씨 반응을 TweetPost 리스트로 가져온다.
        List<TweetPost> reviewList = csvUtil.readEnglishReview();
        reviewList.forEach(System.out::println);

        // 색인을 한다.
        Directory index = new RAMDirectory();

        // 색인 시 분석기는 기본 분석기를 사용한다.
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        // TweetPost 리스트를 색인한다.
        IndexService.indexingTweetData(reviewList, index, config);

        // 유의어 검색을 위해 SynonymMap을 생성한다.
        SynonymMap.Builder builder = new SynonymMap.Builder(true);
        builder.add(new CharsRef("nice"), new CharsRef("good"), true);

        // 쿼리를 생성할 때 SynonymAnalyzer를 사용한다.
        Query q = new QueryParser("text", new SynonymAnalyzer(builder.build())).parse("nice");

        // 쿼리로 검색하여 결과를 확인한다.
        SearchService.searchTweetData(index, q);
    }



}
