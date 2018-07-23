package example;

import analyzer.SynonymAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.CharsRef;
import service.IndexService;
import service.SearchService;
import util.CsvLoader;
import vo.TweetPost;

import java.util.List;

public class SynonymIndexTimeExample {
    public static void main(String args[]) throws Exception{
        //CSV 파일로 부터 데이터를 읽어온다.
        CsvLoader csvUtil = new CsvLoader();
        List<TweetPost> reviewList = csvUtil.readEnglishReview();
        reviewList.forEach(System.out::println);

        //색인을 한다.
        Directory index = new RAMDirectory();

        // 동의어를 처리하기 위해 SynonymMap 설정한다.
        SynonymMap.Builder builder = new SynonymMap.Builder(true);
        builder.add(new CharsRef("good"), new CharsRef("nice"), true);
        builder.add(new CharsRef("great"), new CharsRef("nice"), true);

        // 색인 시 사용할 분석기를 설정한다.
        Analyzer analyzer = new SynonymAnalyzer(builder.build());

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // SynonymAnalyzer로 TweetPost 데이터를 색인한다.
        IndexService.indexingTweetData(reviewList, index, config);

        // 검색 시는 StandardAnalyzer를 사용한다.
        Query q = new QueryParser("text", new StandardAnalyzer()).parse("nice");

        // 쿼리로 검색한 결과를 확인한다.
        SearchService.searchTweetData(index, q);
    }
}
